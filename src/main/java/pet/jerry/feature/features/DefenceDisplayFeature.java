package pet.jerry.feature.features;

import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.hud.TextHUDElement;

import java.util.Collections;

@FeatureInfo(id = "defence_display", name = "Defence Display")
public class DefenceDisplayFeature extends TextHUDElement {
	public DefenceDisplayFeature() {
		super(() -> Collections.singletonList("" + Jerry.INSTANCE.getSkyBlock().getPlayingUser().getDefence()));
		this.getTextColour().setColour(0x00FF33);
	}
}
