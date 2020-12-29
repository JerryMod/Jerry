package pet.jerry.data;

import java.util.UUID;

public interface SkyBlockUser {
	UUID getUUID();
	String getUsername();
	int getHealth();
}
