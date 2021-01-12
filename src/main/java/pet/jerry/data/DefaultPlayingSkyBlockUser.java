package pet.jerry.data;

import net.minecraft.client.Minecraft;
import pet.jerry.data.base.PlayingSkyBlockUser;
import pet.jerry.data.base.SkyBlockUser;

import java.util.UUID;

public class DefaultPlayingSkyBlockUser implements PlayingSkyBlockUser {
	private static final Minecraft mc = Minecraft.getMinecraft();

	private final UUID uuid = mc.thePlayer.getGameProfile().getId();
	private final String username = mc.thePlayer.getGameProfile().getName();

	private String currentIsland;

	private int health = 0;
	private int maxHealth = 0;

	private int defence = 0;

	private int mana = 0;
	private int maxMana = 0;

	private int strength = 0;
	private int critDamage = 0;
	private int critChance = 0;
	private int attackSpeed = 0;

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

	public int getStrength() {
		return strength;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public int getCritChance() {
		return critChance;
	}

	public int getCritDamage() {
		return critDamage;
	}

	void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	void setCritChance(int critChance) {
		this.critChance = critChance;
	}

	void setCritDamage(int critDamage) {
		this.critDamage = critDamage;
	}

	void setStrength(int strength) {
		this.strength = strength;
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
