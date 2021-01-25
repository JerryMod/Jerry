package pet.jerry.data;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pet.jerry.Jerry;
import pet.jerry.event.SkyBlockConnectionEvent;

import java.util.Locale;

@SideOnly(Side.CLIENT)
public class ScoreboardListener {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private long lastSkyBlockScoreboard = -1L;
	private boolean inSkyBlock = false;

	@SubscribeEvent
	public void onUpdate(TickEvent.ClientTickEvent event) {
		if (mc.theWorld != null && mc.thePlayer != null && !mc.isIntegratedServerRunning() && mc.getCurrentServerData() != null) {
			if (mc.getCurrentServerData().serverIP.toLowerCase(Locale.ROOT).endsWith("hypixel.net")) {
				boolean anyScoreboard = false;
				boolean skyblockScoreboardFound = false;
				Scoreboard sb = mc.theWorld.getScoreboard();
				for (ScoreObjective scoreObjective : sb.getScoreObjectives()) {
					anyScoreboard = true;
					if (scoreObjective.getName().equalsIgnoreCase("SBScoreboard")) {
						skyblockScoreboardFound = true;
						break;
					}
				}

				if (anyScoreboard) {
					if (!skyblockScoreboardFound && inSkyBlock) {
						MinecraftForge.EVENT_BUS.post(new SkyBlockConnectionEvent.Exit());
						inSkyBlock = false;
					} else if (skyblockScoreboardFound && !inSkyBlock) {
						MinecraftForge.EVENT_BUS.post(new SkyBlockConnectionEvent.Enter());
						inSkyBlock = true;
					}
				} else {
					if (System.currentTimeMillis() - lastSkyBlockScoreboard >= 10_000 && inSkyBlock) {
						MinecraftForge.EVENT_BUS.post(new SkyBlockConnectionEvent.Exit());
					}
				}

				if (skyblockScoreboardFound) {
					lastSkyBlockScoreboard = System.currentTimeMillis();

					if (Jerry.INSTANCE.getSkyBlock() instanceof DefaultSkyBlock) {
						DefaultSkyBlock skyBlock = (DefaultSkyBlock) Jerry.INSTANCE.getSkyBlock();
						for (ScorePlayerTeam team : sb.getTeams()) {
							String asDisplayed = (team.getColorPrefix() + team.getColorSuffix()).trim();

							String coloursStripped = asDisplayed.replaceAll("\247.", "");
							if (coloursStripped.isEmpty() || coloursStripped.startsWith("www."))
								continue;

							if (coloursStripped.startsWith("⏣")) { // location
								String location = coloursStripped.substring(1).trim();
								skyBlock.setLocation(location);
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		if (this.inSkyBlock) {
			MinecraftForge.EVENT_BUS.post(new SkyBlockConnectionEvent.Exit());
			this.inSkyBlock = false;
		}
	}
}
