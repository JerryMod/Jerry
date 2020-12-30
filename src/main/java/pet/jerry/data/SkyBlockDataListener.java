package pet.jerry.data;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pet.jerry.Jerry;
import pet.jerry.data.base.PlayingSkyBlockUser;
import pet.jerry.data.mock.MockPlayingSkyBlockUser;

@SideOnly(Side.CLIENT)
public class SkyBlockDataListener {
	private static final Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onEvent(ClientChatReceivedEvent event) {
		if (event.type == 2) {
			if(!(Jerry.INSTANCE.getSkyBlock().getPlayingUser() instanceof DefaultPlayingSkyBlockUser))
				return;

			String actionBar = event.message.getFormattedText();
			String[] actionBarSplit = actionBar.split(" ");
			for (String piece : actionBarSplit) {
				String trimmed = piece.trim().replaceAll("\247.", "");

				if(trimmed.isEmpty())
					continue;

				DefaultPlayingSkyBlockUser skyBlockUser = (DefaultPlayingSkyBlockUser) Jerry.INSTANCE.getSkyBlock().getPlayingUser();
				if(trimmed.endsWith("❤")) {
					parseAndSetHealth(trimmed.substring(0, trimmed.length() - 1), skyBlockUser);
				} else if(trimmed.endsWith("❈")) {
					parseAndSetDefence(trimmed.substring(0, trimmed.length() - 1), skyBlockUser);
				} else if(trimmed.endsWith("✎")) {
					parseAndSetMana(trimmed.substring(0, trimmed.length() - 1), skyBlockUser);
				}
			}

		}
	}

	private void parseAndSetHealth(String actionBarSegment, DefaultPlayingSkyBlockUser skyBlockUser) throws NumberFormatException {
		String[] split = actionBarSegment.split("/", 2);
		int currentHealth = Integer.parseInt(split[0]);
		int maxHealth = Integer.parseInt(split[1]);
		skyBlockUser.setHealth(currentHealth);
		skyBlockUser.setMaxHealth(maxHealth);
	}

	private void parseAndSetDefence(String actionBarSegment, DefaultPlayingSkyBlockUser skyBlockUser) throws NumberFormatException {
		int defence = Integer.parseInt(actionBarSegment);
		skyBlockUser.setDefence(defence);
	}

	private void parseAndSetMana(String actionBarSegment, DefaultPlayingSkyBlockUser skyBlockUser) throws NumberFormatException {
		String[] split = actionBarSegment.split("/", 2);
		int currentMana = Integer.parseInt(split[0]);
		int maxMana = Integer.parseInt(split[1]);
		skyBlockUser.setMana(currentMana);
		skyBlockUser.setMaxMana(maxMana);
	}
}
