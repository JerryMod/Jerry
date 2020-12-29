package pet.jerry.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public final class Utils {
	public static void addChatMessage(String msg) { // dear lord why
		Minecraft.getMinecraft().thePlayer.addChatMessage(
				new ChatComponentText("[J] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN))
						.appendSibling(new ChatComponentText(msg).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RESET))));
	}
}
