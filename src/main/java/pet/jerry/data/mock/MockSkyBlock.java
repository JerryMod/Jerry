package pet.jerry.data.mock;

import pet.jerry.data.base.Dungeon;
import pet.jerry.data.base.PlayingSkyBlockUser;
import pet.jerry.data.base.SkyBlock;

public class MockSkyBlock implements SkyBlock {
	private final PlayingSkyBlockUser playingSkyBlockUser = new MockPlayingSkyBlockUser();
	private final Dungeon dungeon = new MockDungeon();

	@Override
	public PlayingSkyBlockUser getPlayingUser() {
		return playingSkyBlockUser;
	}

	@Override
	public Dungeon getCurrentDungeon() {
		return dungeon;
	}

	@Override
	public String getLocation() {
		return "The Catacombs";
	}

	@Override
	public String getSkyBlockDate() {
		return "Late Winter 23rd";
	}
}
