package pet.jerry.feature.features;

import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.AbstractToggleableFeature;

@FeatureInfo(id = "anti_wipe", name = "Anti-Wipe")
public class AntiWipeFeature extends AbstractToggleableFeature {
	public AntiWipeFeature() {
		this.setEnabled(true);
	}
}
