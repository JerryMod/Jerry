package pet.jerry.value;

import pet.jerry.value.constraint.Constraint;

public class BooleanValue extends SimpleValue<Boolean> {
	@SafeVarargs
	public BooleanValue(String name, String id, boolean value, Constraint<Boolean>... constraints) {
		super(name, id, value, constraints);
	}
	@SafeVarargs
	public BooleanValue(String name, String id, Constraint<Boolean>... constraints) {
		super(name, id, false, constraints);
	}

	public void toggle() {
		this.setValue(!this.getValue());
	}
}
