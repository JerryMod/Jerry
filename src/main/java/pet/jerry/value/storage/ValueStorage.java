package pet.jerry.value.storage;

public interface ValueStorage<T> {
	T get();
	void set(T value);
}
