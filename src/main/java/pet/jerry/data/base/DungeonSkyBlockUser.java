package pet.jerry.data.base;

import pet.jerry.data.DungeonClass;

public interface DungeonSkyBlockUser extends SkyBlockUser {
	int getHealth();
	int getClassLevel();
	DungeonClass getDungeonClass();
}
