package pet.jerry.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import pet.jerry.command.subcommands.JerryAboutSubCommand;
import pet.jerry.command.subcommands.JerryLoadSubCommand;
import pet.jerry.command.subcommands.JerrySaveSubCommand;
import pet.jerry.command.subcommands.JerryToggleSubCommand;
import pet.jerry.util.Utils;

import java.util.*;

public class JerryCommand implements ICommand {
	private final Set<JerrySubCommand> subCommands = new HashSet<>();

	public JerryCommand() {
		subCommands.add(new JerryAboutSubCommand());
		subCommands.add(new JerryToggleSubCommand());
		subCommands.add(new JerrySaveSubCommand());
		subCommands.add(new JerryLoadSubCommand());
	}

	private JerrySubCommand getSubCommand(String name) {
		for (JerrySubCommand subCommand : subCommands) {
			if (name.equalsIgnoreCase(subCommand.getName())) {
				return subCommand;
			}
		}

		return null;
	}

	@Override
	public String getCommandName() {
		return "jerry";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/jerry"; // todo more stuff
	}

	@Override
	public List<String> getCommandAliases() {
		return Collections.emptyList();
	}

	@Override
	public void processCommand(ICommandSender ignored, String[] args) throws CommandException {
		if (args.length >= 1) {
			JerrySubCommand subCommand = this.getSubCommand(args[0]);
			if (null == subCommand) {
				throw new CommandException("jerry.subcommand.notfound");
			} else {
				if (args.length > 1)
					args = Arrays.copyOfRange(args, 1, args.length);
				subCommand.onExecute(args);
			}
		} else {
			for (JerrySubCommand subCommand : subCommands) {
				Utils.addChatMessage("/jerry " + subCommand.getName());
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return sender.equals(Minecraft.getMinecraft().thePlayer);
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return Collections.emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

	@Override
	public int compareTo(ICommand o) {
		return o.compareTo(this);
	}
}
