package pet.jerry.value;

import pet.jerry.value.constraint.Constraint;

public class BooleanValue extends SimpleValue<Boolean> {
	@SafeVarargs
	public BooleanValue(String name, String id, Boolean value, Constraint<Boolean>... constraints) {
		super(name, id, value, Boolean.class, constraints);
	}

	public void toggle() {
		this.setValue(!this.getValue());
	}
}
