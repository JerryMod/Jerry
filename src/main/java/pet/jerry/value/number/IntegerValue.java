package pet.jerry.value.number;

import pet.jerry.value.constraint.Constraint;
import pet.jerry.value.storage.ValueStorage;

public class IntegerValue extends NumberValue<Integer> {
	public IntegerValue(String name, String id, int value, int min, int max) {
		super(name, id, value, min, max);
	}

	@SafeVarargs
	public IntegerValue(String name, String id, int value, Constraint<Integer>... constraints) {
		super(name, id, value, constraints);
	}

	public IntegerValue(String name, String id, ValueStorage<Integer> valueStorage, Constraint<Integer>... constraints) {
		super(name, id, valueStorage, constraints);
	}
}
