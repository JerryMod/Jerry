package pet.jerry.feature;

import net.minecraft.client.Minecraft;
import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.value.Saveable;
import pet.jerry.value.Value;
import pet.jerry.value.reflection.ReflectedValueFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFeature implements Feature {
	protected static final Minecraft mc = Minecraft.getMinecraft();

	private final List<Saveable<?>> contents = new ArrayList<>();

	private final String id;
	private final String name;

	public AbstractFeature() {
		if (this.getClass().isAnnotationPresent(FeatureInfo.class)) {
			FeatureInfo info = this.getClass().getAnnotation(FeatureInfo.class);
			this.id = info.id();
			this.name = info.name();

			for (Value<?> value : new ReflectedValueFinder(this).find()) {
				this.add(value);
			}
			info.category().add(this);
		} else {
			throw new RuntimeException("Cannot construct AbstractFeature with missing @FeatureInfo: " + this.getClass().getCanonicalName());
		}
	}

	public AbstractFeature(String id, String name) {
		this(id, name, FeatureCategory.UNCATEGORIZED);
	}

	public AbstractFeature(String id, String name, FeatureCategory category) {
		this.id = id;
		this.name = name;
		category.add(this);
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
		return contents;
	}

	public void add(Saveable<?>... saveables) {
		this.contents.addAll(Arrays.asList(saveables));
	}
}
