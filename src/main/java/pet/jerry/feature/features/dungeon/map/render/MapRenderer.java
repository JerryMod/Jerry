package pet.jerry.feature.features.dungeon.map.render;

import net.minecraft.world.storage.MapData;
import pet.jerry.feature.features.dungeon.DungeonMapDisplayFeature;
import pet.jerry.value.DefaultSaveableContainer;

public interface MapRenderer {
	void drawMap(MapData mapData);
	DefaultSaveableContainer getConfiguration();
	DungeonMapDisplayFeature getParentFeature();
}
