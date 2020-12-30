package pet.jerry.value;

import pet.jerry.core.Named;

public interface Saveable<T> extends Named {
	T getValue();
}
