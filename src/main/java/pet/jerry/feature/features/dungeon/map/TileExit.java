package pet.jerry.feature.features.dungeon.map;

public class TileExit {
	private final int colour;
	private final ConnectionType.Direction direction;

	public TileExit(int colour, ConnectionType.Direction direction) {
		this.colour = colour;
		this.direction = direction;
	}

	public int getColour() {
		return colour;
	}

	public ConnectionType.Direction getDirection() {
		return direction;
	}

	public enum ExitType {
		NONE(0),
		WITHER_DOOR(0xff0d0d0d),
		BLOOD_DOOR(0xffff0000),
		UNDISCOVERED(0xff414141),
		NORMAL(0xff72431b),
		PUZZLE(0xffb24cd8),
		TRAP(0xffd87f33),
		GOLD(0xffe5e533),
		FAIRY(0xfff27fa5);

		private final int colour;

		ExitType(int colour) {
			this.colour = colour;
		}

		public int getColour() {
			return colour;
		}

		public static ExitType getByColour(int colour) {
			for (ExitType value : ExitType.values()) {
				if(value.getColour() == colour)
					return value;
			}

			return NONE;
		}
	}
}
