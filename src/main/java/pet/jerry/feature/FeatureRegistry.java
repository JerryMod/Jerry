package pet.jerry.feature;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.event.SkyBlockConnectionEvent;
import pet.jerry.feature.features.*;
import pet.jerry.feature.features.bar.HealthBarFeature;
import pet.jerry.feature.features.bar.ManaBarFeature;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class FeatureRegistry {
	private final Set<Feature> features = new HashSet<>();

	public FeatureRegistry() {
		registerFeature(new AntiWipeFeature());
		registerFeature(new CoordinatesDisplayFeature());
		registerFeature(new DefenceDisplayFeature());
		registerFeature(new HealthBarFeature());
		registerFeature(new HealthDisplayFeature());
		registerFeature(new ManaBarFeature());
		registerFeature(new ManaDisplayFeature());
		registerFeature(new SlotLockFeature());
		registerFeature(new SpeedDisplayFeature());
	}

	public void registerFeature(Feature feature) {
		features.add(feature);
	}

	public Feature getFeatureByID(String id) {
		for (Feature feature : features) {
			if(feature.getID().equalsIgnoreCase(id)) {
				return feature;
			}
		}

		return null;
	}

	public boolean isEnabled(String id) {
		Feature f = this.getFeatureByID(id);
		return f instanceof AbstractToggleableFeature && ((AbstractToggleableFeature) f).isEnabled();
	}

	public Set<Feature> getFeatures() {
		return Collections.unmodifiableSet(features);
	}

	@SubscribeEvent
	public void onSkyBlockJoin(SkyBlockConnectionEvent.Enter event) {
		for (Feature feature : features) {
			MinecraftForge.EVENT_BUS.register(feature);
		}
	}

	@SubscribeEvent
	public void onSkyBlockLeave(SkyBlockConnectionEvent.Exit event) {
		for (Feature feature : features) {
			MinecraftForge.EVENT_BUS.unregister(feature);
		}
	}
}
