package pet.jerry.screen;

import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.screen.ToggleSwitchElement;

public class FeatureToggleSwitchElement extends ToggleSwitchElement {
	private final AbstractToggleableFeature feature;

	public FeatureToggleSwitchElement(int id, AbstractToggleableFeature feature) {
		super(id);
		this.feature = feature;
	}

	@Override
	protected boolean isEnabled() {
		return feature.isEnabled();
	}

	@Override
	protected void toggle() {
		feature.toggle();
	}
}
