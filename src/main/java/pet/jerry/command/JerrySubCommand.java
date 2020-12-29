package pet.jerry.command;

import net.minecraft.command.CommandException;

public abstract class JerrySubCommand {
	private final String name;

	public JerrySubCommand(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void onExecute(String[] args) throws CommandException;
}
