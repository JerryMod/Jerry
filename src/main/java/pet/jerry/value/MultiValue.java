package pet.jerry.value;

import pet.jerry.value.constraint.Constraint;

import java.util.ArrayList;
import java.util.List;

public class MultiValue<T> extends SimpleValue<List<T>> {
	@SafeVarargs
	public MultiValue(String name, String id, List<T> value, Constraint<List<T>>... constraints) {
		super(name, id, value, null, constraints);
	}

	@SafeVarargs
	public MultiValue(String name, String id, Constraint<List<T>>... constraints) {
		super(name, id, new ArrayList<>(), null, constraints);
	}

	public void add(T value) {
		this.getValue().add(value);
	}

	public void remove(T value) {
		this.getValue().remove(value);
	}

	public boolean contains(T value) {
		return this.getValue().contains(value);
	}
}
