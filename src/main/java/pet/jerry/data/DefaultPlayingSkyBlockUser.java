package pet.jerry.data;

import net.minecraft.client.Minecraft;
import pet.jerry.data.base.PlayingSkyBlockUser;
import pet.jerry.data.base.SkyBlockUser;

import java.util.UUID;

public class DefaultPlayingSkyBlockUser implements PlayingSkyBlockUser {
	private static final Minecraft mc = Minecraft.getMinecraft();

	private final UUID uuid = mc.thePlayer.getGameProfile().getId();
	private final String username = mc.thePlayer.getGameProfile().getName();

	private int health = 0;
	private int maxHealth = 0;

	private int defence = 0;

	private int mana = 0;
	private int maxMana = 0;

	DefaultPlayingSkyBlockUser() { }

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
	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public int getDefence() {
		return defence;
	}

	@Override
	public int getMana() {
		return mana;
	}

	@Override
	public int getMaxMana() {
		return maxMana;
	}

	@Override
	public int getSpeed() {
		return Math.round(mc.thePlayer.capabilities.getWalkSpeed() * 1000);
	}

	void setHealth(int health) {
		this.health = health;
	}

	void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	void setDefence(int defence) {
		this.defence = defence;
	}

	void setMana(int mana) {
		this.mana = mana;
	}

	void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
}
