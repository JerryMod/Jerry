package pet.jerry.feature.features.dungeon.map;

import java.util.*;

public class GridTile {
	private final int colour;
	private final int x, y;
	private final MapGrid mapGrid;
	private final TileDecorations decoration;
	private final Map<ConnectionType.Direction, TileExit> exits;

	public GridTile(MapGrid mapGrid, int x, int y) {
		this.mapGrid = mapGrid;
		this.x = x;
		this.y = y;
		this.colour = mapGrid.getColourAtTile(this);
		this.decoration = assignDecoration();
		this.exits = new HashMap<>();
	}

	public Map<ConnectionType.Direction, TileExit> getExits() {
		return exits;
	}

	private TileDecorations assignDecoration() {
		int middleColour = mapGrid.getColourAtTile(this, this.mapGrid.getTileSize() / 2,
				this.mapGrid.getTileSize() / 2);
		if(middleColour != this.colour) {
			for (TileDecorations decoration : TileDecorations.values()){
				if(middleColour == decoration.getColour()) {
					return decoration;
				}
			}
		}
		return TileDecorations.NONE;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRenderX() {
		return (x * (this.mapGrid.getTileSize() + this.mapGrid.getTilePadding())) + mapGrid.getMinX();
	}

	public int getRenderY() {
		return (y * (this.mapGrid.getTileSize() + this.mapGrid.getTilePadding())) + mapGrid.getMinY();
	}

	public int getTileSize() {
		return mapGrid.getTileSize();
	}

	public int getColour() {
		return colour;
	}

	public TileDecorations getDecoration() {
		return decoration;
	}

	public MapGrid getMapGrid() {
		return mapGrid;
	}
}
