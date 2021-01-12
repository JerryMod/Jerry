package pet.jerry.feature.features.stat;

import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;

@FeatureInfo(name = "Crit Chance Display", id = "crit_chance_display")
public class CritChanceDisplayFeature extends StatDisplayFeature {
	public CritChanceDisplayFeature() {
		super((skyBlock) -> skyBlock.getPlayingUser().getCritChance() + "%", "☣", 0x0033FF);
	}
}
