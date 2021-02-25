package pet.jerry.value;

import java.util.List;

public interface SaveableContainer extends Saveable<List<Saveable<?>>> {
	void add(Saveable<?> saveable);
}
