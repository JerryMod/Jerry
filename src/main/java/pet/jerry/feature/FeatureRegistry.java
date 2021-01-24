package pet.jerry.feature;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.core.Registry;
import pet.jerry.event.SkyBlockConnectionEvent;
import pet.jerry.feature.features.*;
import pet.jerry.feature.features.bar.HealthBarFeature;
import pet.jerry.feature.features.bar.ManaBarFeature;
import pet.jerry.feature.features.dungeon.BlazeRoomSolver;
import pet.jerry.feature.features.dungeon.DisableBossMessages;
import pet.jerry.feature.features.dungeon.DungeonDeathsCounter;
import pet.jerry.feature.features.stat.*;

import java.util.*;

public final class FeatureRegistry implements Registry<Feature> {
	private final List<Feature> features = new ArrayList<>();

	public FeatureRegistry() {
		register(new AntiWipeFeature());
		register(new AttackSpeedDisplayFeature());
		register(new CoordinatesDisplayFeature());
		register(new CritChanceDisplayFeature());
		register(new CritDamageDisplayFeature());
		register(new DisableBossMessages());
		register(new DefenceDisplayFeature());
		register(new HealthBarFeature());
		register(new HealthDisplayFeature());
		register(new ManaBarFeature());
		register(new ManaDisplayFeature());
		register(new ShowSkyBlockIDs());
		register(new SlotLockFeature());
		register(new SpeedDisplayFeature());
		register(new StrengthDisplayFeature());

		register(new BlazeRoomSolver());
		register(new DungeonDeathsCounter());;
	}

	@Override
	public void register(Feature feature) {
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

	@Override
	public List<Feature> getItems() {
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
