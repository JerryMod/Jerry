package pet.jerry.feature.features.bar;

import pet.jerry.Jerry;

public class ManaBarFeature extends BarFeature {
	public ManaBarFeature() {
		super("Mana",
				() -> Jerry.INSTANCE.getSkyBlock().getPlayingUser().getMana(),
				() -> Jerry.INSTANCE.getSkyBlock().getPlayingUser().getMaxMana());
		this.setBarColour(0xFF1111FF);
	}
}
