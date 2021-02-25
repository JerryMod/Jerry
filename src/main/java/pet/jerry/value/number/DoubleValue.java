package pet.jerry.value.number;

import pet.jerry.value.constraint.Constraint;
import pet.jerry.value.storage.ValueStorage;

public class DoubleValue extends NumberValue<Double> {
	public DoubleValue(String name, String id, double value, double min, double max) {
		super(name, id, value, min, max);
	}

	@SafeVarargs
	public DoubleValue(String name, String id, double value, Constraint<Double>... constraints) {
		super(name, id, value, constraints);
	}

	public DoubleValue(String name, String id, ValueStorage<Double> valueStorage, Constraint<Double>... constraints) {
		super(name, id, valueStorage, constraints);
	}
}
