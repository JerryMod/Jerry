package pet.jerry.feature.features.dungeon.map;

public enum ConnectionType {
	DOOR,
	ROOM,
	NONE;

	public enum Direction {
		NORTH {
			@Override
			public ConnectionTestResult testConnection(GridTile gridTile) {
				RoomType roomType = gridTile.getMapGrid()
						.getRoomTypeAtTile(gridTile, 0, -1);
				TileExit.ExitType exitType = gridTile.getMapGrid()
						.getExitTypeAtTile(gridTile, gridTile.getTileSize() / 2, -1);
				if (RoomType.NO_ROOM != roomType) {
					return new ConnectionTestResult(roomType);
				} else if (TileExit.ExitType.NONE != exitType) {
					return new ConnectionTestResult(exitType);
				} else {
					return new ConnectionTestResult();
				}
			}
		},
		EAST {
			@Override
			public ConnectionTestResult testConnection(GridTile gridTile) {
				RoomType type1 = gridTile.getMapGrid()
						.getRoomTypeAtTile(gridTile, gridTile.getTileSize() + 1, 0);
				TileExit.ExitType type2 = gridTile.getMapGrid()
						.getExitTypeAtTile(gridTile, gridTile.getTileSize() + 1, gridTile.getTileSize() / 2);

				if(RoomType.NO_ROOM != type1) {
					return new ConnectionTestResult(type1);
				} else if(TileExit.ExitType.NONE != type2) {
					return new ConnectionTestResult(type2);
				} else {
					return new ConnectionTestResult();
				}
			}
		},
		SOUTH {
			@Override
			public ConnectionTestResult testConnection(GridTile gridTile) {
				RoomType roomType = gridTile.getMapGrid()
						.getRoomTypeAtTile(gridTile, 0, gridTile.getTileSize() + 1);
				TileExit.ExitType exitType = gridTile.getMapGrid()
						.getExitTypeAtTile(gridTile, gridTile.getTileSize() / 2, gridTile.getTileSize() + 1);
				if (RoomType.NO_ROOM != roomType) {
					return new ConnectionTestResult(roomType);
				} else if (TileExit.ExitType.NONE != exitType) {
					return new ConnectionTestResult(exitType);
				} else {
					return new ConnectionTestResult();
				}
			}
		},
		WEST {
			@Override
			public ConnectionTestResult testConnection(GridTile gridTile) {
				RoomType type1 = gridTile.getMapGrid()
						.getRoomTypeAtTile(gridTile, -1, 0);
				TileExit.ExitType type2 = gridTile.getMapGrid()
						.getExitTypeAtTile(gridTile, -1, gridTile.getTileSize() / 2);

				if(RoomType.NO_ROOM != type1) {
					return new ConnectionTestResult(type1);
				} else if(TileExit.ExitType.NONE != type2) {
					return new ConnectionTestResult(type2);
				} else {
					return new ConnectionTestResult();
				}
			}
		};

		public abstract ConnectionTestResult testConnection(GridTile gridTile);
	}

	public static class ConnectionTestResult {
		private final int colour;
		private final ConnectionType connectionType;

		public ConnectionTestResult() {
			this(-1, NONE);
		}

		public ConnectionTestResult(int colour, ConnectionType connectionType) {
			this.colour = colour;
			this.connectionType = connectionType;
		}

		public ConnectionTestResult(RoomType roomType) {
			this(roomType.getRoomMapColour(), ROOM);
		}

		public ConnectionTestResult(TileExit.ExitType roomType) {
			this(roomType.getColour(), DOOR);
		}

		public int getColour() {
			return colour;
		}

		public ConnectionType getConnectionType() {
			return connectionType;
		}
	}
}
