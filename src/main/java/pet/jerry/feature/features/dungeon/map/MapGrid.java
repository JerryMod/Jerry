package pet.jerry.feature.features.dungeon.map;

import net.minecraft.block.material.MapColor;
import net.minecraft.world.storage.MapData;

import java.util.*;

public class MapGrid {
	private final int mapSize = 128;

	private final byte[][] roomIDs;
	private final MapData mapData;
	private final int tilePadding;
	private int tileSize;
	private int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
	private int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
	private final Map<Byte, Room> rooms = new HashMap<>();

	public MapGrid(MapData mapData) {
		this.tilePadding = 4;
		this.mapData = mapData;
		this.roomIDs = createGrid();
		for (byte[] arr : this.roomIDs) {
			Arrays.fill(arr, (byte) -1);
		}

		this.populate();
	}

	private byte[][] createGrid() {
		RoomType previousRoomType = null;
		int entranceStartY = -1;
		int entranceEndY = -1;
		for (int x = 0; x < mapSize; x++) {
			for (int y = 0; y < mapSize; y++) {
				RoomType typeAtCoords = this.getRoomTypeAt(x, y);
				if (typeAtCoords != RoomType.NO_ROOM) {
					if (typeAtCoords == RoomType.ENTRANCE
							&& previousRoomType == RoomType.NO_ROOM) {
						entranceStartY = y;
					}
					if (x < minX)
						minX = x;
					if (y < minY)
						minY = y;
				} else {
					if (x > maxX)
						maxX = x;
					if (y > maxY)
						maxY = y;
					if (previousRoomType == RoomType.ENTRANCE) {
						entranceEndY = y;

					}
				}
				previousRoomType = typeAtCoords;
			}
		}

		maxX++;
		this.tileSize = entranceEndY - entranceStartY;
		int tileWithPadding = tileSize + tilePadding;
		int xSize = (maxX - minX) / tileWithPadding;
		int ySize = (maxY - minY) / tileWithPadding;
		if(xSize < 0 || ySize < 0)
			return new byte[0][0];
		return new byte[xSize][ySize];
	}

	private void populate() {
		if (roomIDs.length == 0)
			return;

		byte assignableID = 1;
		for (int x = 0; x < this.roomIDs.length; x++) {
			for (int y = 0; y < this.roomIDs[0].length; y++) {
				byte idAt = this.roomIDs[x][y];
				int screenX = (x * (this.tileSize + this.tilePadding)) + minX;
				int screenY = (y * (this.tileSize + this.tilePadding)) + minY;
				RoomType typeAtCoords = this.getRoomTypeAt(screenX, screenY);
				if (idAt != -1 || typeAtCoords == RoomType.NO_ROOM)
					continue;
				List<GridTile> tiles = new ArrayList<>();
				this.assignID(x, y, assignableID, tiles);
				if (tiles.isEmpty())
					continue;

				byte[][] cropped = this.cropByID(assignableID);

				Room.Shape roomShape = this.getMatchingShape(cropped);

				int colour = -1;
				for (GridTile tile : tiles) {
					if (colour > -1 && tile.getColour() != colour) {
						//System.err.println("Attempt to create room with multiple tile colours!");
						continue;
					}
					colour = tile.getColour();
				}
				RoomType roomType = this.getRoomTypeAtTile(tiles.get(0), 0, 0);
				Room room = new Room(assignableID, roomShape, cropped, roomType, tiles, this);

				this.rooms.putIfAbsent(assignableID, room);
				assignableID++;
			}
		}
	}

	private List<GridTile> assignID(int x, int y, byte id, List<GridTile> tiles) {
		if (!areValidArrayCoordinates(x, y))
			return tiles;

		if (this.roomIDs[x][y] != -1)
			return tiles;
		this.roomIDs[x][y] = id;
		GridTile thisTile = new GridTile(this, x, y);
		Map<ConnectionType.Direction, ConnectionType.ConnectionTestResult> connections = this.getConnections(thisTile);
		for (Map.Entry<ConnectionType.Direction, ConnectionType.ConnectionTestResult> entry : connections.entrySet()) {
			if (entry.getValue().getConnectionType() == ConnectionType.NONE)
				continue;

			if (entry.getValue().getConnectionType() == ConnectionType.ROOM) {
				switch (entry.getKey()) {
					case SOUTH:
						this.assignID(x, y + 1, id, tiles);
						break;
					case NORTH:
						this.assignID(x, y - 1, id, tiles);
						break;
					case EAST:
						this.assignID(x + 1, y, id, tiles);
						break;
					case WEST:
						this.assignID(x - 1, y, id, tiles);
						break;
				}
			} else if (entry.getValue().getConnectionType() == ConnectionType.DOOR) {
				GridTile connectedTile = this.getTileInDirection(thisTile, entry.getKey());
				if (null != connectedTile) {
					ConnectionType.Direction direction = getOppositeDirection(entry.getKey());
					if (connectedTile.getExits().containsKey(direction)) {
						continue;
					}
				}
				thisTile.getExits().put(entry.getKey(), new TileExit(entry.getValue().getColour(), entry.getKey()));
			}
		}
		tiles.add(thisTile);
		return tiles;
	}

	private ConnectionType.Direction getOppositeDirection(ConnectionType.Direction direction) {
		switch (direction) {
			case EAST:
				return ConnectionType.Direction.WEST;
			case WEST:
				return ConnectionType.Direction.EAST;
			case SOUTH:
				return ConnectionType.Direction.NORTH;
			case NORTH:
				return ConnectionType.Direction.SOUTH;
		}
		return null;
	}

	private GridTile getTileInDirection(GridTile tile, ConnectionType.Direction direction) {
		int x = tile.getX();
		int y = tile.getY();

		switch (direction) {
			case NORTH:
				y -= 1;
				break;
			case SOUTH:
				y += 1;
			case WEST:
				x -= 1;
				break;
			case EAST:
				x += 1;
				break;
		}

		if (x >= this.roomIDs.length || y >= this.roomIDs[0].length)
			return null;

		if (x < 0 || y < 0)
			return null;

		byte id = this.roomIDs[x][y];
		if (id == -1)
			return null;

		Room room = this.rooms.get(id);
		if (null == room)
			return null;

		for (GridTile roomTile : room.getTiles()) {
			if (roomTile.getX() == x && roomTile.getY() == y) {
				return roomTile;
			}
		}
		return null;
	}

	private List<GridTile> assignID(int x, int y, byte id) {
		return this.assignID(x, y, id, new ArrayList<>());
	}

	private Map<ConnectionType.Direction, ConnectionType.ConnectionTestResult> getConnections(GridTile tile) {
		final Map<ConnectionType.Direction, ConnectionType.ConnectionTestResult> result = new HashMap<>();
		for (ConnectionType.Direction direction : ConnectionType.Direction.values()) {
			result.put(direction, direction.testConnection(tile));
		}
		return result;
	}

	private Room.Shape getShapeForID(byte id) {
		return this.getMatchingShape(this.cropByID(this.roomIDs, id));
	}

	private Room.Shape getMatchingShape(byte[][] input) {
		if (null == input) {
			return null;
		}

		for (Room.Shape shapes : Room.Shape.values()) {
			byte[][] pattern = shapes.getPattern();
			for (int i = 0; i < 4; i++) {
				pattern = Room.Shape.rotateArray(pattern);
				if (Arrays.deepEquals(pattern, input)) {
					return shapes;
				}
			}
		}

		return null;
	}

	private byte[][] cropByID(byte id) {
		return this.cropByID(this.roomIDs, id);
	}

	private byte[][] cropByID(byte[][] input, byte id) {
		int lowestX = Integer.MAX_VALUE;
		int lowestY = Integer.MAX_VALUE;

		int highestX = Integer.MIN_VALUE;
		int highestY = Integer.MIN_VALUE;

		for (int x = 0; x < input.length; x++) {
			for (int y = 0; y < input[0].length; y++) {
				byte idAt = input[x][y];
				if (idAt == id) {
					lowestX = Math.min(lowestX, x);
					lowestY = Math.min(lowestY, y);

					highestX = Math.max(highestX, x);
					highestY = Math.max(highestY, y);
				}
			}
		}

		if (lowestX == Integer.MAX_VALUE || lowestY == Integer.MAX_VALUE
				|| highestX == Integer.MIN_VALUE || highestY == Integer.MIN_VALUE)
			return null;

		int xDiff = highestX - lowestX + 1;
		int yDiff = highestY - lowestY + 1;

		byte[][] result = new byte[xDiff][yDiff];
		for (int xx = lowestX; xx <= highestX; xx++) {
			for (int yy = lowestY; yy <= highestY; yy++) {
				byte originalID = input[xx][yy];
				result[xx - lowestX][yy - lowestY] = (byte) (originalID == id ? 1 : 0);
			}
		}

		return result;
	}

	public RoomType getRoomTypeAtTile(GridTile tile, int offsetX, int offsetY) {
		return this.getRoomTypeAt(tile.getRenderX() + offsetX, tile.getRenderY() + offsetY);
	}

	public RoomType getRoomTypeAt(int x, int y) {
		RoomType type = RoomType.getByColour(this.getColourAtCoordinates(x, y));
		return type == null ? RoomType.NO_ROOM : type;
	}

	public TileExit.ExitType getExitTypeAtTile(GridTile tile, int offsetX, int offsetY) {
		return this.getExitTypeAt(tile.getRenderX() + offsetX, tile.getRenderY() + offsetY);
	}

	public TileExit.ExitType getExitTypeAt(int x, int y) {
		TileExit.ExitType type = TileExit.ExitType.getByColour(this.getColourAtCoordinates(x, y));
		return type == null ? TileExit.ExitType.NONE : type;
	}

	public int getColourAtTile(GridTile tile) {
		return this.getColourAtTile(tile, 0, 0);
	}

	public int getColourAtTile(GridTile tile, int offsetX, int offsetY) {
		return this.getColourAtCoordinates(tile.getRenderX() + offsetX, tile.getRenderY() + offsetY);
	}

	public int getColourAtCoordinates(int x, int y) {
		try {
			byte colour = mapData.colors[x + y * this.mapSize];
			if (colour / 4 == 0) {
				return 0;
			}
			MapColor mapColor = MapColor.mapColorArray[colour / 4];
			return mapColor.getMapColor(colour & 3);
		} catch (Exception e) {
			return 0;
		}
	}

	private boolean areValidArrayCoordinates(int x, int y) {
		return x >= 0 && x < this.roomIDs.length && y >= 0 && y < this.roomIDs[0].length;
	}

	public byte[][] getRoomIDs() {
		return roomIDs;
	}

	public Map<Byte, Room> getRooms() {
		return rooms;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getTilePadding() {
		return tilePadding;
	}
}
