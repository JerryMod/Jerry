package pet.jerry.feature.features.dungeon.map;

public enum RoomType {
	NO_ROOM(0xff000000),
	UNDISCOVERED(0xff414141),
	ENTRANCE(0xff007c00),
	NORMAL(0xff72431b),
	BLOOD(0xffff0000),
	PUZZLE(0xffb24cd8),
	TRAP(0xffd87f33),
	GOLD(0xffe5e533),
	FAIRY(0xfff27fa5);

	private final int roomMapColour;

	RoomType(int roomMapColour) {
		this.roomMapColour = roomMapColour;
	}

	public int getRoomMapColour() {
		return roomMapColour;
	}

	public static RoomType getByColour(int colour) {
		for (RoomType value : RoomType.values()) {
			if(value.getRoomMapColour() == colour)
				return value;
		}

		return null;
	}
}
