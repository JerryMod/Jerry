package pet.jerry.feature.features;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.event.InventorySlotClickEvent;
import pet.jerry.event.InventorySlotDrawEvent;
import pet.jerry.event.ItemDropEvent;
import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.value.MultiValue;

@FeatureInfo(id = "slot_locking", name = "Slot Locking")
public class SlotLockFeature extends AbstractToggleableFeature {
	private final MultiValue<Integer> lockedSlots = new MultiValue<>("Locked Slots", "locked_slots");
	private final KeyBinding slotLockKeybind
			= new KeyBinding("key.jerry.lockslot", Keyboard.KEY_L, "key.category.jerry");

	public SlotLockFeature() {
		this.getContainer().add(lockedSlots);
		ClientRegistry.registerKeyBinding(slotLockKeybind);
	}

	@SubscribeEvent
	void onSlotClick(InventorySlotClickEvent event) {
		if (lockedSlots.contains(event.getSlot().getSlotIndex())
				&& event.getSlot().inventory.equals(mc.thePlayer.inventory)) {
			mc.thePlayer.playSound("mob.villager.no", 1f, 1f);
			event.setCanceled(true);
		}
	}

	// todo allow more styles
	@SubscribeEvent
	void onSlotDraw(InventorySlotDrawEvent.Pre event) {
		Slot slot = event.getSlot();
		if (lockedSlots.contains(slot.getSlotIndex())
				&& event.getSlot().inventory.equals(mc.thePlayer.inventory)) {
			Gui.drawRect(slot.xDisplayPosition, slot.yDisplayPosition,
					slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, 0x99FF0000);
		}
	}

	@SubscribeEvent
	void onDrop(ItemDropEvent event) {
		if(lockedSlots.contains(mc.thePlayer.inventory.currentItem) && Jerry.INSTANCE.getSkyBlock().getCurrentDungeon() == null) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	void onKeyDown(GuiScreenEvent.KeyboardInputEvent.Pre event) {
		if (event.gui instanceof GuiContainer && Keyboard.isKeyDown(this.slotLockKeybind.getKeyCode())) {
			event.setCanceled(true);
			Slot slot = ((GuiContainer) event.gui).getSlotUnderMouse();
			if(!slot.inventory.equals(mc.thePlayer.inventory))
				return;

			if (lockedSlots.contains(slot.getSlotIndex())) {
				lockedSlots.remove(slot.getSlotIndex());
			} else {
				lockedSlots.add(slot.getSlotIndex());
			}
		}
	}
}
