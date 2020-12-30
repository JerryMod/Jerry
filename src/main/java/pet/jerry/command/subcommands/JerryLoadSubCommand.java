package pet.jerry.command.subcommands;

import net.minecraft.command.CommandException;
import pet.jerry.Jerry;
import pet.jerry.command.JerrySubCommand;
import pet.jerry.util.Utils;

public class JerryLoadSubCommand extends JerrySubCommand {
	public JerryLoadSubCommand() {
		super("load");
	}

	@Override
	public void onExecute(String[] args) throws CommandException {
		try {
			Jerry.INSTANCE.getConfigRegistry().load();
			Utils.addChatMessage("Configuration loaded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
