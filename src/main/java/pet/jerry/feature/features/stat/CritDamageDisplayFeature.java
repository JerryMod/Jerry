package pet.jerry.feature.features.stat;

import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.category.FeatureCategory;

@FeatureInfo(name = "Crit Damage Display", id = "crit_damage_display", category = FeatureCategory.INFORMATION)
public class CritDamageDisplayFeature extends StatDisplayFeature {
	public CritDamageDisplayFeature() {
		super((skyBlock) -> skyBlock.getPlayingUser().getCritDamage(), "☠", 0x0033FF);
	}
}
