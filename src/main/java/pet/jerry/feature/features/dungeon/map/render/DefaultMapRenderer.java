package pet.jerry.feature.features.dungeon.map.render;

import net.minecraft.client.Minecraft;
import net.minecraft.world.storage.MapData;
import pet.jerry.feature.features.dungeon.DungeonMapDisplayFeature;
import pet.jerry.value.DefaultSaveableContainer;

public class DefaultMapRenderer implements MapRenderer {
	private final DefaultSaveableContainer container = new DefaultSaveableContainer("Default Map Options", "default_map");
	private final DungeonMapDisplayFeature parent;

	public DefaultMapRenderer(DungeonMapDisplayFeature parent) {
		this.parent = parent;
	}

	@Override
	public void drawMap(MapData mapData) {
		Minecraft.getMinecraft().entityRenderer
				.getMapItemRenderer().renderMap(mapData, false);
	}

	@Override
	public DefaultSaveableContainer getConfiguration() {
		return container;
	}

	@Override
	public DungeonMapDisplayFeature getParentFeature() {
		return parent;
	}
}
