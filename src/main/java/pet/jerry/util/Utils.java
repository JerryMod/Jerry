package pet.jerry.util;

import net.minecraft.client.Minecraft;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import pet.jerry.Jerry;

public final class Utils {
	private static final IChatComponent CHAT_PREFIX = new ChatComponentText("[J] ")
			.setChatStyle(new ChatStyle()
					.setColor(EnumChatFormatting.GREEN)
					.setBold(true)
			.setChatHoverEvent(
					new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ChatComponentText("This message was created by " + Jerry.MOD_NAME + " version " + Jerry.VERSION)))); // dear lord why


	public static void addChatMessage(String msg) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(CHAT_PREFIX.createCopy()
						.appendSibling(new ChatComponentText(msg).setChatStyle(new ChatStyle()
								.setBold(false).setColor(EnumChatFormatting.RESET).setChatHoverEvent(null))));
	}
}
