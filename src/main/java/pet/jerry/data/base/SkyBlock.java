package pet.jerry.data.base;

public interface SkyBlock {
	PlayingSkyBlockUser getPlayingUser();
	Dungeon getCurrentDungeon();
	String getLocation();
	String getSkyBlockDate(); // todo make this a data type?

}
