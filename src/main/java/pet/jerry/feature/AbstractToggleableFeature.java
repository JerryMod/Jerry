package pet.jerry.feature;

import pet.jerry.core.Toggleable;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.value.BooleanValue;

public abstract class AbstractToggleableFeature extends AbstractFeature implements Toggleable {
	private final BooleanValue enabled = new BooleanValue("Enabled", "enabled", false);

	public AbstractToggleableFeature() {
		this.add(enabled);
	}

	public AbstractToggleableFeature(String id, String name) {
		this(id, name, FeatureCategory.UNCATEGORIZED);
	}

	public AbstractToggleableFeature(String id, String name, FeatureCategory category) {
		super(id, name, category);
		this.add(enabled);
	}

	@Override
	public boolean isEnabled() {
		return enabled.getValue();
	}

	@Override
	public void setEnabled(boolean state) {
		enabled.setValue(state);
	}

	@Override
	public void toggle() {
		this.setEnabled(!this.enabled.getValue());
	}
}
