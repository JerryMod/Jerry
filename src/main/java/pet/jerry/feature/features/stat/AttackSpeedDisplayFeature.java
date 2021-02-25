package pet.jerry.feature.features.stat;

import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.category.FeatureCategory;

@FeatureInfo(name = "Attack Speed Display", id = "attack_speed_display", category = FeatureCategory.INFORMATION)
public class AttackSpeedDisplayFeature extends StatDisplayFeature {
	public AttackSpeedDisplayFeature() {
		super((skyBlock) -> skyBlock.getPlayingUser().getAttackSpeed(), "⚔", 0xFFFF33);
	}
}
