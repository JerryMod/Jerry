package pet.jerry.feature.features;

import pet.jerry.annotation.FeatureInfo;
import pet.jerry.hud.Position;
import pet.jerry.hud.TextHUDElement;

import java.util.Collections;

@FeatureInfo(id = "speed_display", name = "Speed Percentage Display")
public class SpeedDisplayFeature extends TextHUDElement {
	public SpeedDisplayFeature() {
		super(new Position(0.1f, 0.1f),
				() -> Collections.singletonList("" + Math.round(mc.thePlayer.capabilities.getWalkSpeed() * 1000) + "%"));
	}
}
