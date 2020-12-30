package pet.jerry.event;

import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Event;

public class InventorySlotClickEvent extends Event {
	private final Slot slot;

	public InventorySlotClickEvent(Slot slot) {
		this.slot = slot;
	}

	public Slot getSlot() {
		return slot;
	}

	@Override
	public boolean isCancelable() {
		return true;
	}
}
