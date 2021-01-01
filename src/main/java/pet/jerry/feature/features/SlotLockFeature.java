package pet.jerry.feature.features;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
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
import pet.jerry.hud.NamedColour;
import pet.jerry.value.BooleanValue;
import pet.jerry.value.SimpleValue;

import java.util.HashSet;
import java.util.Set;

@FeatureInfo(id = "slot_locking", name = "Slot Locking")
public class SlotLockFeature extends AbstractToggleableFeature {
	private static final ResourceLocation LOCK_IMG = new ResourceLocation("jerry:gui/lock.png");

	private final SimpleValue<Set<Integer>> lockedSlots
			= new SimpleValue<>("Locked Slots", "locked_slots", new HashSet<>());
	private final BooleanValue allowUlt
			= new BooleanValue("Allow Ult in Dungeons", "allow_ult", true);
	private final SimpleValue<Style> style
			= new SimpleValue<>("Style", "style", Style.LOCK);
	private final NamedColour lockedSlotColour
			= new NamedColour("Locked Slot Colour", "locked_slot_colour");
	private final KeyBinding slotLockKeybind
			= new KeyBinding("key.jerry.lockslot", Keyboard.KEY_L, "key.category.jerry");

	public SlotLockFeature() {
		this.getContainer().add(lockedSlots, allowUlt, style, lockedSlotColour);
		ClientRegistry.registerKeyBinding(slotLockKeybind);
	}

	private void playLockedSlotInteractSound() {
		mc.thePlayer.playSound("mob.villager.no", 1f, 1f);
	}

	@SubscribeEvent
	void onSlotClick(InventorySlotClickEvent event) {
		if (lockedSlots.getValue().contains(event.getSlot().getSlotIndex())
				&& event.getSlot().inventory.equals(mc.thePlayer.inventory)) {
			event.setCanceled(true);
			playLockedSlotInteractSound();
		}
	}

	@SubscribeEvent
	void onSlotDraw(InventorySlotDrawEvent.Pre event) {
		if(!this.style.getValue().equals(Style.BACKGROUND)) return;
		Slot slot = event.getSlot();
		if (lockedSlots.getValue().contains(slot.getSlotIndex())
				&& event.getSlot().inventory.equals(mc.thePlayer.inventory)) {
			Gui.drawRect(slot.xDisplayPosition, slot.yDisplayPosition,
					slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, lockedSlotColour.toHex());
		}
	}

	@SubscribeEvent
	void onSlotDrawPost(InventorySlotDrawEvent.Post event) {
		if(!this.style.getValue().equals(Style.LOCK)) return;
		Slot slot = event.getSlot();
		if(lockedSlots.getValue().contains(slot.getSlotIndex())
				&& event.getSlot().inventory.equals(mc.thePlayer.inventory)) {
			GlStateManager.pushMatrix();
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.disableDepth();
			GlStateManager.color(1f, 0.1f, 0.1f, 0.3f);
			mc.getTextureManager().bindTexture(LOCK_IMG);
			int dimension = 14;
			Gui.drawModalRectWithCustomSizedTexture(slot.xDisplayPosition + 1, slot.yDisplayPosition + 1,
					0, 0, dimension, dimension, dimension, dimension);
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableDepth();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}

	@SubscribeEvent
	void onDrop(ItemDropEvent event) {
		if (lockedSlots.getValue().contains(mc.thePlayer.inventory.currentItem)
				&& (Jerry.INSTANCE.getSkyBlock().getCurrentDungeon() == null || !this.allowUlt.getValue())) {
			event.setCanceled(true);
			playLockedSlotInteractSound();
		}
	}

	@SubscribeEvent
	void onKeyDown(GuiScreenEvent.KeyboardInputEvent.Pre event) {
		if (event.gui instanceof GuiContainer && Keyboard.isKeyDown(this.slotLockKeybind.getKeyCode())) {
			event.setCanceled(true);
			Slot slot = ((GuiContainer) event.gui).getSlotUnderMouse();
			if (!slot.inventory.equals(mc.thePlayer.inventory))
				return;

			if (lockedSlots.getValue().contains(slot.getSlotIndex())) {
				lockedSlots.getValue().remove(slot.getSlotIndex());
			} else {
				lockedSlots.getValue().add(slot.getSlotIndex());
			}
		}
	}

	private enum Style {
		BACKGROUND,
		LOCK
	}
}
