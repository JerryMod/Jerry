package pet.jerry.value;

import java.util.*;

public class DefaultSaveableContainer implements SaveableContainer {
	private final List<Saveable<?>> contents = new LinkedList<>();
	private final String name;
	private final String id;

	public DefaultSaveableContainer(String name, String id) {
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
	public List<Saveable<?>> getValue() {
		return contents;
	}

	@Override
	public void add(Saveable<?> saveable) {
		this.contents.add(saveable);
	}

	public void add(Saveable<?>... saveables) {
		this.contents.addAll(Arrays.asList(saveables));
	}

	public void add(Collection<Saveable<?>> saveables) {
		this.contents.addAll(saveables);
	}

	public Saveable<?> get(String id) {
		for (Saveable<?> saveable : this.getValue()) {
			if(saveable.getID().equalsIgnoreCase(id))
				return saveable;
		}

		return null;
	}
}
