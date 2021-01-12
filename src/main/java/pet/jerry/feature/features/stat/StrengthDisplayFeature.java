package pet.jerry.feature.features.stat;

import pet.jerry.annotation.FeatureInfo;

@FeatureInfo(name = "Strength Display", id = "strength_display")
public class StrengthDisplayFeature extends StatDisplayFeature {
	public StrengthDisplayFeature() {
		super((skyBlock) -> skyBlock.getPlayingUser().getStrength(), "❁", 0xFF3300);
	}
}
