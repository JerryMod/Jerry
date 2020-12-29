package pet.jerry.value;

import pet.jerry.value.constraint.Constraint;

import java.util.*;

public class SimpleValue<T> implements Value<T> {
	private final String name;
	private final String id;
	private final Set<Constraint<T>> constraints = new HashSet<>();
	private T value;

	public SimpleValue(String name, String id, T value) {
		this.name = name;
		this.id = id;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public T getValue() {
		return null;
	}

	@Override
	public void setValue(T value) {
		for (Constraint<T> constraint : constraints) {
			if(!constraint.validate(value))
				throw new IllegalArgumentException(
						String.format("Constraint validation failed for property \"%s\" (%s)", this.name,
								constraint.getClass().getCanonicalName()));
		}
		this.value = value;
	}

	@Override
	public Set<Constraint<T>> getConstraints() {
		return Collections.unmodifiableSet(constraints);
	}

	@Override
	public void addConstraint(Constraint<T> constraint) {
		constraints.add(constraint);
	}
}
