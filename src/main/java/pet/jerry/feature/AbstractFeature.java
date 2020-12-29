package pet.jerry.feature;

import net.minecraft.client.Minecraft;
import pet.jerry.annotation.FeatureInfo;

public abstract class AbstractFeature implements Feature {
	protected static final Minecraft mc = Minecraft.getMinecraft();

	private final String id;
	private final String name;

	public AbstractFeature() {
		if(this.getClass().isAnnotationPresent(FeatureInfo.class)) {
			FeatureInfo info = this.getClass().getAnnotation(FeatureInfo.class);
			this.id = info.id();
			this.name = info.name();
		} else {
			throw new RuntimeException("@FeatureInfo annotation missing from " + this.getClass().getCanonicalName());
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getID() {
		return id;
	}
}
