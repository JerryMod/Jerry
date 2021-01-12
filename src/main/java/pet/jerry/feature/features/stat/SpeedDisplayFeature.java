package pet.jerry.feature.features.stat;

import pet.jerry.annotation.FeatureInfo;

import java.util.Collections;

@FeatureInfo(id = "speed_display", name = "Speed Percentage Display")
public class SpeedDisplayFeature extends StatDisplayFeature {
	public SpeedDisplayFeature() {
		super((skyBlock) -> Math.round(mc.thePlayer.capabilities.getWalkSpeed() * 1000) + "%", "✦");
	}
}
