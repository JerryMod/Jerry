package pet.jerry.feature.features.stat;

import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;

import java.util.Collections;

@FeatureInfo(id = "defence_display", name = "Defence Display")
public class DefenceDisplayFeature extends StatDisplayFeature {
	public DefenceDisplayFeature() {
		super((skyBlock) -> skyBlock.getPlayingUser().getDefence(), "❈", 0x00FF33);
	}

	@Override
	protected String applySymbol(String symbol, String value) {
		return value + symbol;
	}
}
