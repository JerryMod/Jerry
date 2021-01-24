package pet.jerry.feature.features.bar;

public class ManaBarFeature extends BarFeature {
	public ManaBarFeature() {
		super("Mana",
				(skyBlock) -> skyBlock.getPlayingUser().getMana(),
				(skyBlock) -> skyBlock.getPlayingUser().getMaxMana());
		this.setBarColour(0xFF1111FF);
	}
}
