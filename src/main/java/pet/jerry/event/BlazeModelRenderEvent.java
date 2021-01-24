package pet.jerry.event;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BlazeModelRenderEvent extends Event {
	private final EntityBlaze blaze;

	protected BlazeModelRenderEvent(EntityBlaze blaze) {
		this.blaze = blaze;
	}

	public EntityBlaze getBlaze() {
		return blaze;
	}

	public static class Pre extends BlazeModelRenderEvent {
		public Pre(EntityBlaze blaze) {
			super(blaze);
		}
	}

	public static class Post extends BlazeModelRenderEvent {
		public Post(EntityBlaze blaze) {
			super(blaze);
		}
	}
}
