package pet.jerry.value;

import pet.jerry.value.constraint.Constraint;
import pet.jerry.value.storage.BasicValueStorage;
import pet.jerry.value.storage.ValueStorage;

import java.util.*;

public class SimpleValue<T> implements Value<T> {
	private final String name;
	private final String id;
	private final Set<Constraint<T>> constraints = new HashSet<>();
	private final ValueStorage<T> valueStorage;

	@SafeVarargs
	public SimpleValue(String name, String id, T value, Constraint<T>... constraints) {
		this(name, id, new BasicValueStorage<>(value), constraints);
	}

	@SafeVarargs
	public SimpleValue(String name, String id, ValueStorage<T> storage, Constraint<T>... constraints) {
		this.name = name;
		this.id = id;
		this.valueStorage = storage;
		this.constraints.addAll(Arrays.asList(constraints));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public T getValue() {
		return valueStorage.get();
	}

	@Override
	public void setValue(T value) {
		for (Constraint<T> constraint : constraints) {
			if(!constraint.validate(value)) {
				System.err.printf("Constraint validation failed for property \"%s\" (%s)%n", this.name,
						constraint.getClass().getCanonicalName());
				return;
			}
		}
		this.valueStorage.set(value);
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
