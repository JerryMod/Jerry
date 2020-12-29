package pet.jerry.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class DungeonConnectionEvent {
	private DungeonConnectionEvent() {}

	public static class Enter extends Event {}
	public static class Exit  extends Event {}
}
