package pet.jerry.data.mock;

import pet.jerry.data.DungeonClass;
import pet.jerry.data.base.DungeonSkyBlockUser;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MockDungeonSkyBlockUser implements DungeonSkyBlockUser {
	private final UUID uuid;
	private final String username;
	private final int classLevel;
	private final DungeonClass dungeonClass;
	private final int health;

	MockDungeonSkyBlockUser(UUID uuid, String username, DungeonClass dungeonClass) {
		this.uuid = uuid;
		this.username = username;
		this.classLevel = ThreadLocalRandom.current().nextInt(20, 31);
		this.dungeonClass = dungeonClass;
		this.health = ThreadLocalRandom.current().nextInt(4567, 9876);
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public int getHealth() {
		return 1234;
	}

	@Override
	public int getClassLevel() {
		return classLevel;
	}

	@Override
	public DungeonClass getDungeonClass() {
		return dungeonClass;
	}
}
