package pet.jerry.value.storage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectedValueStorage<T> implements ValueStorage<T> {
	private final Field backingField;
	private final Object parentObject;

	public ReflectedValueStorage(Field backingField, Object parentObject) {
		this.backingField = backingField;
		this.parentObject = parentObject;
		if(Modifier.isFinal(backingField.getModifiers())) {
			throw new RuntimeException("Cannot create ReflectedValueStorage on final field: "
					+ this.backingField.getDeclaringClass().getCanonicalName() + "." + this.backingField.getName());
		}

		this.backingField.setAccessible(true);
	}

	@Override
	public T get() {
		try {
			return (T) backingField.get(parentObject);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void set(T value) {
		try {
			backingField.set(parentObject, value);
		} catch (Exception e) {
		}
	}
}
