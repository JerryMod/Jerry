package pet.jerry.feature.features.stat;

import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;

@FeatureInfo(name = "Crit Damage Display", id = "crit_damage_display")
public class CritDamageDisplayFeature extends StatDisplayFeature {
	public CritDamageDisplayFeature() {
		super((skyBlock) -> skyBlock.getPlayingUser().getCritDamage(), "☠", 0x0033FF);
	}
}
