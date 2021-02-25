package pet.jerry.value;

import pet.jerry.value.constraint.Constraint;

public class EnumValue<T extends Enum<T>> extends SimpleValue<T> {
	@SafeVarargs
	public EnumValue(String name, String id, T value, Constraint<T>... constraints) {
		super(name, id, value, constraints);
	}
}
