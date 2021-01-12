package pet.jerry.feature;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.event.SkyBlockConnectionEvent;
import pet.jerry.feature.features.*;
import pet.jerry.feature.features.bar.HealthBarFeature;
import pet.jerry.feature.features.bar.ManaBarFeature;
import pet.jerry.feature.features.stat.*;

import java.util.*;

public final class FeatureRegistry {
	private final List<Feature> features = new ArrayList<>();

	public FeatureRegistry() {
		registerFeature(new AntiWipeFeature());
		registerFeature(new AttackSpeedDisplayFeature());
		registerFeature(new CoordinatesDisplayFeature());
		registerFeature(new CritChanceDisplayFeature());
		registerFeature(new CritDamageDisplayFeature());
		registerFeature(new CrowdSilencerFeature());
		registerFeature(new DefenceDisplayFeature());
		registerFeature(new HealthBarFeature());
		registerFeature(new HealthDisplayFeature());
		registerFeature(new ManaBarFeature());
		registerFeature(new ManaDisplayFeature());
		registerFeature(new SlotLockFeature());
		registerFeature(new SpeedDisplayFeature());
		registerFeature(new StrengthDisplayFeature());
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

	public List<Feature> getFeatures() {
		return Collections.unmodifiableList(features);
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
