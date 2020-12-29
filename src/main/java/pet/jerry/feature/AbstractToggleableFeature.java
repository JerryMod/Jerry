package pet.jerry.feature;

import pet.jerry.core.Toggleable;

public abstract class AbstractToggleableFeature extends AbstractFeature implements Toggleable {
	private boolean enabled;

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean state) {
		enabled = state;
	}

	@Override
	public void toggle() {
		this.setEnabled(!this.enabled);
	}
}
