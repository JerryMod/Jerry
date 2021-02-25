package pet.jerry.feature.features.dungeon.map.render.custom;

import pet.jerry.feature.features.dungeon.map.GridTile;
import pet.jerry.feature.features.dungeon.map.Room;
import pet.jerry.feature.features.dungeon.map.TileDecorations;

public abstract class IconRenderer {
	private boolean shouldCentre = false;

	public boolean shouldCentre() {
		return shouldCentre;
	}

	public abstract void drawIcon(Room room, float x, float y);

	protected TileDecorations getRoomDecoration(Room room) {
		for (GridTile tile : room.getTiles()) {
			if (tile.getDecoration() != TileDecorations.NONE)
				return tile.getDecoration();
		}

		return TileDecorations.NONE;
	}

	public void setShouldCentre(boolean shouldCentre) {
		this.shouldCentre = shouldCentre;
	}
}
