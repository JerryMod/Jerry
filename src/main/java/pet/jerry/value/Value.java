package pet.jerry.value;

import pet.jerry.value.constraint.Constraint;

import java.util.Set;

public interface Value<T> extends Saveable<T> {
	void setValue(T value) throws IllegalArgumentException;

	Set<Constraint<T>> getConstraints();

	void addConstraint(Constraint<T> constraint);
}
