package pet.jerry.data.base;

import java.util.UUID;

public interface SkyBlockUser {
	UUID getUUID();
	String getUsername();
	int getHealth();
}
