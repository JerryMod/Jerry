package pet.jerry.feature.features;

import pet.jerry.annotation.FeatureInfo;
import pet.jerry.feature.AbstractToggleableFeature;

@FeatureInfo(id = "anti_wipe", name = "Anti-Wipe")
public class AntiWipeFeature extends AbstractToggleableFeature {
	public AntiWipeFeature() {
		this.setEnabled(true); //todo remove this when enabled states are saved
	}
}
