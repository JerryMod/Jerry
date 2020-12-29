package pet.jerry.data;

import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class DungeonSkyBlockUser implements SkyBlockUser {
	private final UUID uuid;
	private final String username;
	private int health = 20;

	DungeonSkyBlockUser(EntityOtherPlayerMP player) {
		this(player.getGameProfile().getId(), player.getGameProfile().getName());
	}

	DungeonSkyBlockUser(UUID uuid, String username) {
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

	void setHealth(int health) {
		this.health = health;
	}
}
