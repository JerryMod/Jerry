package pet.jerry.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public final class SkyBlockConnectionEvent {
	private SkyBlockConnectionEvent() {}

	public static class Enter extends Event { }
	public static class Exit  extends Event { }
}
