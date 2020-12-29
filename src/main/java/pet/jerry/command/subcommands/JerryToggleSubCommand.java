package pet.jerry.command.subcommands;

import net.minecraft.command.CommandException;
import pet.jerry.Jerry;
import pet.jerry.command.JerrySubCommand;
import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.feature.Feature;
import pet.jerry.util.Utils;

public class JerryToggleSubCommand extends JerrySubCommand {
	public JerryToggleSubCommand() {
		super("toggle");
	}

	@Override
	public void onExecute(String[] args) throws CommandException {
		if(args.length < 1) {
			throw new CommandException("You need at least one argument for this command.");
		} else {
			Feature feature = Jerry.INSTANCE.getFeatureRegistry().getFeatureByID(args[0]);
			if(null == feature) {
				throw new CommandException("Feature with ID " + args[0] + " not found.");
			}

			if(!(feature instanceof AbstractToggleableFeature)) {
				throw new CommandException("That feature cannot be toggled!");
			}

			AbstractToggleableFeature toggleableFeature = (AbstractToggleableFeature) feature;
			toggleableFeature.toggle();
			Utils.addChatMessage(String.format("%s has been %s.", feature.getName(), toggleableFeature.isEnabled() ? "enabled" : "disabled"));
		}
	}
}
