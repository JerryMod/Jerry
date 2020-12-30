package pet.jerry.data;

import pet.jerry.data.base.Dungeon;
import pet.jerry.data.base.DungeonSkyBlockUser;

import java.util.HashSet;
import java.util.Set;

public class DefaultDungeon implements Dungeon {
	private Set<DungeonSkyBlockUser> dungeonTeam = new HashSet<>();
	private final int floor;
	private final DungeonType dungeonType;

	DefaultDungeon(int floor, DungeonType dungeonType) {
		this.floor = floor;
		this.dungeonType = dungeonType;
	}

	public int getFloor() {
		return floor;
	}

	public Set<DungeonSkyBlockUser> getDungeonTeam() {
		return dungeonTeam;
	}

	public DungeonType getDungeonType() {
		return dungeonType;
	}
}
