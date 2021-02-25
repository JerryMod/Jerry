package pet.jerry.screen.configure.element;

import pet.jerry.screen.ToggleSwitchElement;
import pet.jerry.value.Value;

public class ValueToggleSwitch extends ToggleSwitchElement {
	private final Value<Boolean> value;

	public ValueToggleSwitch(int id, Value<Boolean> value) {
		super(id);
		this.value = value;
	}

	@Override
	protected boolean isEnabled() {
		return value.getValue();
	}

	@Override
	protected void toggle() {
		value.setValue(!value.getValue());
	}
}
