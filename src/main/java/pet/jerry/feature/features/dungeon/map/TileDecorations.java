package pet.jerry.feature.features.dungeon.map;

public enum TileDecorations {
	GREEN_CHECK(0xff007c00),
	WHITE_CHECK(0xffffffff),
	PUZZLE_FAIL(0xffff0000),
	NONE(-1);

	private final int colour;

	TileDecorations(int colour) {
		this.colour = colour;
	}

	public int getColour() {
		return colour;
	}
}
