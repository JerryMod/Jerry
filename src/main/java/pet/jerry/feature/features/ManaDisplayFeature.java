package pet.jerry.feature.features;

import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.hud.Position;
import pet.jerry.hud.TextHUDElement;

import java.util.Collections;

@FeatureInfo(id = "mana_display", name = "Mana Display")
public class ManaDisplayFeature extends TextHUDElement {
	public ManaDisplayFeature() {
		super(new Position(0.1f, 0.1f), () -> Collections.singletonList(
				Jerry.INSTANCE.getSkyBlock().getPlayingUser().getMana() + " / " +
						Jerry.INSTANCE.getSkyBlock().getPlayingUser().getMaxMana()
		));
		this.getTextColour().setColour(0x0033FF);
	}
}
