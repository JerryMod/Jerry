package pet.jerry.data;

import pet.jerry.data.base.Dungeon;
import pet.jerry.data.base.DungeonSkyBlockUser;

import java.util.HashSet;
import java.util.Set;

public class DefaultDungeon implements Dungeon {
	private Set<DungeonSkyBlockUser> dungeonTeam = new HashSet<>();
	private final int floor;
	private final DungeonType dungeonType;

	private int secrets = 0;
	private int crypts = 0;
	private int deaths = 0;

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

	@Override
	public int getCryptsDestroyed() {
		return crypts;
	}

	@Override
	public int getSecretsFound() {
		return secrets;
	}

	@Override
	public int getTotalDeaths() {
		return deaths;
	}

	void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	void setCrypts(int crypts) {
		this.crypts = crypts;
	}

	void setSecrets(int secrets) {
		this.secrets = secrets;
	}
}
