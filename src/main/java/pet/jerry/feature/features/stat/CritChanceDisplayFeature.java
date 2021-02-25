package pet.jerry.feature.features.stat;

import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.category.FeatureCategory;

@FeatureInfo(name = "Crit Chance Display", id = "crit_chance_display", category = FeatureCategory.INFORMATION)
public class CritChanceDisplayFeature extends StatDisplayFeature {
	public CritChanceDisplayFeature() {
		super((skyBlock) -> skyBlock.getPlayingUser().getCritChance() + "%", "☣", 0x0033FF);
	}
}
