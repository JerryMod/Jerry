package pet.jerry.event;

import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Event;

public class InventorySlotDrawEvent extends Event {
	private final Slot slot;

	protected InventorySlotDrawEvent(Slot slot) {
		this.slot = slot;
	}

	public Slot getSlot() {
		return slot;
	}

	public static class Pre extends InventorySlotDrawEvent {
		public Pre(Slot slot) {
			super(slot);
		}
	}

	public static class Post extends InventorySlotDrawEvent {
		public Post(Slot slot) {
			super(slot);
		}
	}
}
