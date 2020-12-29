package pet.jerry.command.subcommands;

import net.minecraft.client.Minecraft;
import pet.jerry.Jerry;
import pet.jerry.command.JerrySubCommand;
import pet.jerry.util.Utils;

public class JerryAboutSubCommand extends JerrySubCommand {
	public JerryAboutSubCommand() {
		super("about");
	}

	@Override
	public void onExecute(String[] args) {
		Utils.addChatMessage(String.format("%s version %s", Jerry.MOD_NAME, Jerry.VERSION));
		Minecraft.getMinecraft().thePlayer.playSound("mob.villager.yes", 1f, 1f);
	}
}
