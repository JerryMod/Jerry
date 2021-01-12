package pet.jerry.feature.features;

import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.hud.Position;
import pet.jerry.hud.TextHUDElement;

import java.util.Collections;

@FeatureInfo(id = "health_display", name = "Health Display")
public class HealthDisplayFeature extends TextHUDElement {
	public HealthDisplayFeature() {
		super(new Position(0.1f, 0.2f), (skyBlock) -> Collections.singletonList(
				skyBlock.getPlayingUser().getHealth()
						+ " / " + skyBlock.getPlayingUser().getMaxHealth()
		));
		this.getTextColour().setColour(0xFF0000);
	}
}
