package pet.jerry.data.base;

import pet.jerry.data.DefaultDungeonSkyBlockUser;
import pet.jerry.data.DungeonType;

import java.util.Set;

public interface Dungeon {
	int getFloor();
	Set<DungeonSkyBlockUser> getDungeonTeam();
	DungeonType getDungeonType();
	int getSecretsFound();
	int getCryptsDestroyed();
}
