package pet.jerry.value;

import pet.jerry.core.Named;
import pet.jerry.value.constraint.Constraint;
import java.util.Set;

public interface Value<T> extends Named {
	T getValue();
	void setValue(T value) throws IllegalArgumentException;
	Set<Constraint<T>> getConstraints();
	void addConstraint(Constraint<T> constraint);
}
