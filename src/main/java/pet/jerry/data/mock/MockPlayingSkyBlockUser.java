package pet.jerry.data.mock;

import net.minecraft.client.Minecraft;
import pet.jerry.data.DefaultPlayingSkyBlockUser;
import pet.jerry.data.base.PlayingSkyBlockUser;

import java.util.UUID;

public class MockPlayingSkyBlockUser implements PlayingSkyBlockUser {
	private static final Minecraft mc = Minecraft.getMinecraft();

	private final UUID uuid = mc.getSession().getProfile().getId();
	private final String username = mc.getSession().getProfile().getName();

	MockPlayingSkyBlockUser() { }

	@Override
	public int getMana() {
		return 1234;
	}

	@Override
	public int getMaxMana() {
		return 3456;
	}

	@Override
	public int getDefence() {
		return 1111;
	}

	@Override
	public int getHealth() {
		return 1234;
	}

	@Override
	public int getMaxHealth() {
		return 3456;
	}

	@Override
	public int getSpeed() {
		return 350;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
