package pet.jerry.data;

import net.minecraftforge.common.MinecraftForge;
import pet.jerry.data.base.Dungeon;
import pet.jerry.data.base.SkyBlock;
import pet.jerry.event.DungeonConnectionEvent;

public class DefaultSkyBlock implements SkyBlock {
	private final DefaultPlayingSkyBlockUser ourUser = new DefaultPlayingSkyBlockUser();
	private String location;
	private String skyBlockDate;
	private Dungeon currentDungeon = null;

	@Override
	public String getLocation() {
		return location;
	}

	void setLocation(String location) {
		this.location = location;
	}

	@Override
	public DefaultPlayingSkyBlockUser getPlayingUser() {
		return ourUser;
	}

	@Override
	public String getSkyBlockDate() {
		return skyBlockDate;
	}

	void setSkyBlockDate(String skyBlockDate) {
		this.skyBlockDate = skyBlockDate;
	}

	@Override
	public Dungeon getCurrentDungeon() {
		return currentDungeon;
	}

	void setCurrentDungeon(Dungeon currentDungeon) {
		this.currentDungeon = currentDungeon;
	}
}
