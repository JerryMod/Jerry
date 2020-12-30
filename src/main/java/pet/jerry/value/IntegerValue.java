package pet.jerry.value;

import pet.jerry.value.constraint.Constraint;

public class IntegerValue extends SimpleValue<Integer>{
	@SafeVarargs
	public IntegerValue(String name, String id, Integer value, Constraint<Integer>... constraints) {
		super(name, id, value, constraints);
	}
}
