package pet.jerry.data;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import pet.jerry.data.base.DungeonSkyBlockUser;
import pet.jerry.data.base.SkyBlockUser;

import java.util.UUID;

public class DefaultDungeonSkyBlockUser implements DungeonSkyBlockUser {
	private final UUID uuid;
	private final String username;
	private int health = 0;
	private int classLevel = 0;
	private DungeonClass dungeonClass = DungeonClass.ARCHER;

	DefaultDungeonSkyBlockUser(EntityOtherPlayerMP player) {
		this(player.getGameProfile().getId(), player.getGameProfile().getName());
	}

	DefaultDungeonSkyBlockUser(UUID uuid, String username) {
		this.uuid = uuid;
		this.username = username;
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
		return health;
	}

	@Override
	public int getClassLevel() {
		return classLevel;
	}

	@Override
	public DungeonClass getDungeonClass() {
		return dungeonClass;
	}

	void setHealth(int health) {
		this.health = health;
	}
}
