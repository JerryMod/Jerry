package pet.jerry.value.number;

import pet.jerry.value.SimpleValue;
import pet.jerry.value.constraint.Constraint;
import pet.jerry.value.constraint.NumberClampConstraint;
import pet.jerry.value.storage.ValueStorage;

public abstract class NumberValue<T extends Number> extends SimpleValue<T> {
	public NumberValue(String name, String id, T value, T min, T max) {
		this(name, id, value, new NumberClampConstraint<>(min, max));
	}

	@SafeVarargs
	public NumberValue(String name, String id, T value, Constraint<T>... constraints) {
		super(name, id, value, constraints);
	}

	@SafeVarargs
	public NumberValue(String name, String id, ValueStorage<T> valueStorage, Constraint<T>... constraints) {
		super(name, id, valueStorage, constraints);
	}
}
