package pet.jerry.value.constraint;

public interface Constraint<T> {
	boolean validate(T value);
}
