package pet.jerry.data.mock;

import pet.jerry.data.DungeonClass;
import pet.jerry.data.DungeonType;
import pet.jerry.data.base.Dungeon;
import pet.jerry.data.base.DungeonSkyBlockUser;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MockDungeon implements Dungeon {
	private final Set<DungeonSkyBlockUser> dungeonTeam = new HashSet<>();

	public MockDungeon() {
		dungeonTeam.add(new MockDungeonSkyBlockUser(UUID.fromString("24c725cb-33f9-4932-9f41-24998f07b7ad"),
				"Papika", DungeonClass.MAGE));
		dungeonTeam.add(new MockDungeonSkyBlockUser(UUID.fromString("521303e3-3a69-4539-837b-886a3ed24bf5"),
				"cuute", DungeonClass.ARCHER));
		dungeonTeam.add(new MockDungeonSkyBlockUser(UUID.fromString("366636fd-3b0b-4774-8b1e-f704181120ed"),
				"stepsister___", DungeonClass.HEALER));
	}

	@Override
	public int getFloor() {
		return 7;
	}

	@Override
	public Set<DungeonSkyBlockUser> getDungeonTeam() {
		return dungeonTeam;
	}

	@Override
	public DungeonType getDungeonType() {
		return DungeonType.CATACOMBS;
	}

	@Override
	public int getSecretsFound() {
		return 23;
	}

	@Override
	public int getCryptsDestroyed() {
		return 6;
	}
}
