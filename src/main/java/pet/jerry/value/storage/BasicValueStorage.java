package pet.jerry.value.storage;

public class BasicValueStorage<T> implements ValueStorage<T> {
	private T value;

	public BasicValueStorage(T initialValue) {
		this.value = initialValue;
	}

	@Override
	public T get() {
		return value;
	}

	@Override
	public void set(T value) {
		this.value = value;
	}
}
