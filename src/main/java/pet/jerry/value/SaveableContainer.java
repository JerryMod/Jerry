package pet.jerry.value;

import java.util.HashSet;
import java.util.Set;

public class SaveableContainer implements Saveable<Set<Saveable<?>>> {
	private final Set<Saveable<?>> contents = new HashSet<>();
	private final String name;
	private final String id;

	public SaveableContainer(String name, String id) {
		this.name = name;
		this.id = id;
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
	public Set<Saveable<?>> getValue() {
		return contents;
	}

	public void add(Saveable<?> saveable) {
		this.contents.add(saveable);
	}
}
