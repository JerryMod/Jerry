package pet.jerry.feature.features.dungeon.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Room {
	private final MapGrid mapGrid;

	private final byte id;
	private final List<GridTile> tiles;
	private final Shape shape;
	private final GridTile principalTile;
	private final RoomType type;
	private final byte[][] representation;
	private final int lowestX;
	private final int lowestY;
	private final int lowestRenderX;
	private final int lowestRenderY;

	public Room(byte id, Shape shape, byte[][] cropped, RoomType type, List<GridTile> tiles, MapGrid mapGrid) {
		this.id = id;
		this.shape = shape;
		this.mapGrid = mapGrid;
		this.representation = cropped;
		this.type = type;
		this.tiles = tiles;
		tiles.sort(Comparator.comparingInt(GridTile::getX));
		this.lowestX = tiles.get(0).getX();
		this.lowestRenderX = tiles.get(0).getRenderX();
		tiles.sort(Comparator.comparingInt(GridTile::getY));
		this.lowestY = tiles.get(0).getY();
		this.lowestRenderY = tiles.get(0).getRenderY();

		tiles.sort(Comparator.comparingInt(GridTile::getX).thenComparing(GridTile::getY));
		this.principalTile = this.tiles.get(0);
	}

	public byte getID() {
		return id;
	}

	public Shape getShape() {
		return shape;
	}

	public GridTile getPrincipalTile() {
		return principalTile;
	}

	public RoomType getType() {
		return type;
	}

	public List<GridTile> getTiles() {
		return tiles;
	}

	public byte[][] getRepresentation() {
		return representation;
	}

	public int getLowestRenderX() {
		return lowestRenderX;
	}

	public int getLowestRenderY() {
		return lowestRenderY;
	}

	public int getLowestX() {
		return lowestX;
	}

	public int getLowestY() {
		return lowestY;
	}

	public int getTileSize() {
		return this.principalTile.getTileSize();
	}

	public int getColour() {
		return this.principalTile.getColour();
	}

	public enum Shape {
		TWO_BY_TWO("2x2", new byte[][]{
				{1, 1},
				{1, 1}
		}),
		L_SHAPED("L-shaped", new byte[][]{
				{1, 1},
				{1, 0}
		}),
		ONE_BY_FOUR("1x4", new byte[][]{
				{1},
				{1},
				{1},
				{1}
		}),
		ONE_BY_THREE("1x3", new byte[][]{
				{1},
				{1},
				{1}
		}),
		ONE_BY_TWO("1x2", new byte[][]{
				{1},
				{1}
		}),
		ONE_BY_ONE("1x1", new byte[][]{
				{1},
		});

		private final byte[][] pattern;
		private final String readable;

		Shape(String readableName, byte[][] pattern) {
			this.pattern = pattern;
			this.readable = readableName;
		}

		public String getReadableName() {
			return readable;
		}

		public byte[][] getPattern() {
			return pattern;
		}

		public static byte[][] rotateArray(byte[][] in) {
			final int w = in.length;
			final int h = in[0].length;
			byte[][] out = new byte[h][w];
			for (int r = 0; r < w; r++) {
				for (int c = 0; c < h; c++) {
					out[c][w - 1 - r] = in[r][c];
				}
			}
			return out;
		}
	}
}
