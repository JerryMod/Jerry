package pet.jerry.feature.features.stat;

import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;

@FeatureInfo(name = "Attack Speed Display", id = "attack_speed_display")
public class AttackSpeedDisplayFeature extends StatDisplayFeature {
	public AttackSpeedDisplayFeature() {
		super((skyBlock) -> skyBlock.getPlayingUser().getAttackSpeed(), "⚔", 0xFFFF33);
	}
}
