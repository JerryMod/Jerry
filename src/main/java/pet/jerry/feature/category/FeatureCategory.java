package pet.jerry.feature.category;

import pet.jerry.value.SaveableContainer;
import pet.jerry.value.Saveable;

import java.util.LinkedList;
import java.util.List;

public enum FeatureCategory implements SaveableContainer {
	UNCATEGORIZED("Uncategorized", "uncategorized"),
	DUNGEON("Dungeon", "dungeon"),
	INFORMATION("Information", "information");

	private final String name, id;
	private final List<Saveable<?>> contents = new LinkedList<>();

	FeatureCategory(String name, String id) {
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
	public void add(Saveable<?> saveable) {
		this.contents.add(saveable);
	}

	@Override
	public List<Saveable<?>> getValue() {
		return this.contents;
	}
}