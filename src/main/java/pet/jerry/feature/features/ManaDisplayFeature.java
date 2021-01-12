package pet.jerry.feature.features;

import pet.jerry.annotation.FeatureInfo;
import pet.jerry.hud.Position;
import pet.jerry.hud.TextHUDElement;

import java.util.Collections;

@FeatureInfo(id = "mana_display", name = "Mana Display")
public class ManaDisplayFeature extends TextHUDElement {
	public ManaDisplayFeature() {
		super(new Position(0.1f, 0.1f), (skyBlock) -> Collections.singletonList(
				skyBlock.getPlayingUser().getMana() + " / " + skyBlock.getPlayingUser().getMaxMana()
		));
		this.getTextColour().setColour(0x0033FF);
	}
}
