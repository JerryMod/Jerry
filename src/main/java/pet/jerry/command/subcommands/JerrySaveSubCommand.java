package pet.jerry.command.subcommands;

import net.minecraft.command.CommandException;
import pet.jerry.Jerry;
import pet.jerry.command.JerrySubCommand;
import pet.jerry.util.Utils;

public class JerrySaveSubCommand extends JerrySubCommand {
	public JerrySaveSubCommand() {
		super("save");
	}

	@Override
	public void onExecute(String[] args) throws CommandException {
		try {
			Jerry.INSTANCE.getConfigRegistry().save();
			Utils.addChatMessage("Configuration saved.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
