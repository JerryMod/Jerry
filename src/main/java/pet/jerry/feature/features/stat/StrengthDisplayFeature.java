package pet.jerry.feature.features.stat;

import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.category.FeatureCategory;

@FeatureInfo(name = "Strength Display", id = "strength_display", category = FeatureCategory.INFORMATION)
public class StrengthDisplayFeature extends StatDisplayFeature {
	public StrengthDisplayFeature() {
		super((skyBlock) -> skyBlock.getPlayingUser().getStrength(), "❁", 0xFF3300);
	}
}
