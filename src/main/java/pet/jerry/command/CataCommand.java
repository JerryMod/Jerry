package pet.jerry.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.Collections;
import java.util.List;

public class CataCommand implements ICommand {
	@Override
	public String getCommandName() {
		return "cata";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/cata <floor number>";
	}

	@Override
	public List<String> getCommandAliases() {
		return Collections.emptyList();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(args.length >= 1) {
			try {
				int floor = -1;
				if(args[0].equalsIgnoreCase("e") || args[0].equalsIgnoreCase("entrance")) {
					floor = 0;
				} else {
					floor = Integer.parseInt(args[0]);
				}

				if(floor < 8 && floor > -1) {
					Minecraft.getMinecraft().thePlayer.sendChatMessage("/joindungeon CATACOMBS " + floor);
				} else {
					throw new CommandException("Invalid floor.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommandException("Something went wrong! Did you type a number?");
			}
		} else {
			throw new CommandException(this.getCommandUsage(sender));
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
		return 0;
	}
}
