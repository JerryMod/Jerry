package pet.jerry.feature.features.dungeon.map.render.custom;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.commons.lang3.tuple.Pair;
import pet.jerry.feature.features.dungeon.map.ConnectionType;
import pet.jerry.feature.features.dungeon.map.GridTile;
import pet.jerry.feature.features.dungeon.map.Room;
import pet.jerry.feature.features.dungeon.map.TileExit;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public abstract class RoomRenderer {
	private final Set<Room.Shape> renderableShapes;

	protected RoomRenderer(Set<Room.Shape> renderableShapes) {
		this.renderableShapes = renderableShapes;
	}

	protected RoomRenderer(Room.Shape renderableShape) {
		this(Collections.singleton(renderableShape));
	}

	public Set<Room.Shape> getRenderableShapes() {
		return renderableShapes;
	}

	public abstract void draw(Room room);

	public abstract Pair<Float, Float> getCentrePoint(Room room);

	protected void drawExits(Room room) {
		for (GridTile tile : room.getTiles()) {
			int doorOffset = tile.getTileSize() / 4;
			int relativeX = tile.getRenderX() - room.getLowestRenderX();
			int relativeY = tile.getRenderY()- room.getLowestRenderY();
			GlStateManager.pushMatrix();
			GlStateManager.translate(relativeX, relativeY, 0);
			for (Map.Entry<ConnectionType.Direction, TileExit> entry : tile.getExits().entrySet()) {
				int colour = entry.getValue().getColour() | 0xFF000000;
				switch (entry.getKey()) {
					case SOUTH:
						Gui.drawRect(doorOffset, tile.getTileSize(),
								tile.getTileSize() - doorOffset,
								tile.getTileSize() + 4,
								colour);
						break;
					case NORTH:
						Gui.drawRect(doorOffset, -4,
								tile.getTileSize() - doorOffset,
								0,
								colour);
						break;
					case EAST:
						Gui.drawRect(tile.getTileSize(),
								doorOffset,
								tile.getTileSize() + 4,
								tile.getTileSize() - doorOffset,
								colour);
						break;
					case WEST:
						Gui.drawRect(-4,
								doorOffset,
								0,
								tile.getTileSize() - doorOffset,
								colour);
						break;
				}
			}
			GlStateManager.translate(-relativeX, -relativeY, 0);
			GlStateManager.popMatrix();
		}
	}
}
