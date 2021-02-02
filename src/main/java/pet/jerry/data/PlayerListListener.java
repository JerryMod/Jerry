package pet.jerry.data;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pet.jerry.Jerry;
import pet.jerry.data.mock.MockDungeon;
import pet.jerry.event.DungeonConnectionEvent;

import java.util.function.Consumer;

@SideOnly(Side.CLIENT)
public class PlayerListListener {
	private final Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	void onSomething(TickEvent.ClientTickEvent event) {
		boolean foundDungeon = false;
		if(!(Jerry.INSTANCE.getSkyBlock() instanceof DefaultSkyBlock) || mc.getNetHandler() == null)
			return;

		DefaultSkyBlock skyBlock = ((DefaultSkyBlock) Jerry.INSTANCE.getSkyBlock());
		DefaultPlayingSkyBlockUser user = skyBlock.getPlayingUser();

		for (NetworkPlayerInfo info : mc.getNetHandler().getPlayerInfoMap()) {
			if (null == info || null == info.getDisplayName())
				continue;

			String displayName = info.getDisplayName().getUnformattedText();
			if (!displayName.contains(" "))
				continue;

			String trimmed = displayName.trim();

			try {
				if (trimmed.startsWith("Strength")) {
					this.parseIntWithColon(trimmed, user::setStrength);
				} else if (trimmed.startsWith("Crit Damage")) {
					this.parseIntWithColon(trimmed, user::setCritDamage);
				} else if (trimmed.startsWith("Crit Chance")) {
					this.parseIntWithColon(trimmed, user::setCritChance);
				} else if (trimmed.startsWith("Attack Speed")) {
					this.parseIntWithColon(trimmed, user::setAttackSpeed);
				}

				if (trimmed.startsWith("Dungeon Stats")) {
					foundDungeon = true;
					if(!(skyBlock.getCurrentDungeon() instanceof DefaultDungeon)) {
						if (skyBlock.getLocation().startsWith("The Catacombs")) {
							String location = skyBlock.getLocation().replaceAll("The Catacombs", "").trim()
									.substring(1).replaceAll("\\(", "").replaceAll("\\)", "");
							int floor = 0;
							if (location.startsWith("F")) {
								try {
									floor = Integer.parseInt(location.substring(1));
								} catch (Exception e) {
									System.err.println("Couldn't parse dungeon floor.");
									e.printStackTrace();
								}
							}

							skyBlock.setCurrentDungeon(new DefaultDungeon(floor, DungeonType.CATACOMBS));
							MinecraftForge.EVENT_BUS.post(new DungeonConnectionEvent.Enter());
						}
					}
				}

				if (skyBlock.getCurrentDungeon() instanceof DefaultDungeon) {
					DefaultDungeon dungeon = (DefaultDungeon) skyBlock.getCurrentDungeon();
					if (trimmed.startsWith("Secrets Found")) {
						this.parseIntWithColon(trimmed, dungeon::setSecrets);
					} else if (trimmed.startsWith("Crypts")) {
						this.parseIntWithColon(trimmed, dungeon::setCrypts);
					} else if (trimmed.startsWith("Deaths")) {
						this.parseIntWithColon(trimmed, dungeon::setDeaths);
					}
				}
			} catch (Exception e) {
			}
		}
		if(!foundDungeon) {
			MinecraftForge.EVENT_BUS.post(new DungeonConnectionEvent.Exit());
			skyBlock.setCurrentDungeon(null);
		}
	}

	private void parseIntWithColon(String s, Consumer<Integer> setter) {
		String number = s.split(":", 2)[1].trim()
				.replaceAll("\\(", "").replaceAll("\\)", "");
		if(!Character.isDigit(number.charAt(0)))
			number = number.substring(1);
		setter.accept(Integer.parseInt(number));
	}
}
