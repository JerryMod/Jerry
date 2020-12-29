package pet.jerry.data;

import net.minecraftforge.common.MinecraftForge;
import pet.jerry.event.DungeonConnectionEvent;

public class SkyBlock {
	private final OurSkyBlockUser ourUser = new OurSkyBlockUser();
	private String location;
	private String skyBlockDate;
	private Dungeon currentDungeon = null;

	public String getLocation() {
		return location;
	}

	void setLocation(String location) {
		if(location.equalsIgnoreCase(this.location))
			return;

		if(location.contains("Catacombs"))
			MinecraftForge.EVENT_BUS.post(new DungeonConnectionEvent.Enter());
		else if(this.location.contains("Catacombs"))
			MinecraftForge.EVENT_BUS.post(new DungeonConnectionEvent.Exit());

		this.location = location;
	}

	public OurSkyBlockUser getOurUser() {
		return ourUser;
	}

	public String getSkyBlockDate() {
		return skyBlockDate;
	}

	void setSkyBlockDate(String skyBlockDate) {
		this.skyBlockDate = skyBlockDate;
	}

	public Dungeon getCurrentDungeon() {
		return currentDungeon;
	}

	void setCurrentDungeon(Dungeon currentDungeon) {
		this.currentDungeon = currentDungeon;
	}
}
