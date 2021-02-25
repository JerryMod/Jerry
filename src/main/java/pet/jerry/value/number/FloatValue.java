package pet.jerry.value.number;

import pet.jerry.value.constraint.Constraint;
import pet.jerry.value.storage.ValueStorage;

public class FloatValue extends NumberValue<Float> {
	public FloatValue(String name, String id, float value, float min, float max) {
		super(name, id, value, min, max);
	}

	@SafeVarargs
	public FloatValue(String name, String id, Float value, Constraint<Float>... constraints) {
		super(name, id, value, constraints);
	}

	public FloatValue(String name, String id, ValueStorage<Float> valueStorage, Constraint<Float>... constraints) {
		super(name, id, valueStorage, constraints);
	}
}
