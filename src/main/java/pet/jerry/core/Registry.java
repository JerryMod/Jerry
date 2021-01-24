package pet.jerry.core;

import java.util.Collection;

public interface Registry<T> {
	Collection<T> getItems();
	void register(T item);
}
