package pet.jerry.data;

import java.util.List;

public class Dungeon {
	private List<DungeonSkyBlockUser> dungeonTeam;
	private final int floor;
	private final Type dungeonType;

	Dungeon(int floor, Type dungeonType) {
		this.floor = floor;
		this.dungeonType = dungeonType;
	}

	public int getFloor() {
		return floor;
	}

	public List<DungeonSkyBlockUser> getDungeonTeam() {
		return dungeonTeam;
	}

	public Type getDungeonType() {
		return dungeonType;
	}

	enum Type {
		CATACOMBS
	}
}
