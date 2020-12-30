package pet.jerry.mixins;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pet.jerry.event.InventorySlotClickEvent;
import pet.jerry.event.InventorySlotDrawEvent;

@Mixin(GuiContainer.class)
public class MixinGuiContainer {
	@Inject(
			method = "handleMouseClick",
			cancellable = true,
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;windowClick(IIIILnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;",
					shift = At.Shift.BEFORE
			)
	)
	void onMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType, CallbackInfo ci) {
		if(null != slotIn) {
			InventorySlotClickEvent event = new InventorySlotClickEvent(slotIn);
			MinecraftForge.EVENT_BUS.post(event);
			if(event.isCanceled()) {
				ci.cancel();
			}
		}
	}

	@Inject(
			method = "drawSlot",
			at = @At("HEAD")
	)
	void onSlotDraw(Slot slotIn, CallbackInfo ci) {
		MinecraftForge.EVENT_BUS.post(new InventorySlotDrawEvent.Pre(slotIn));
	}

	@Inject(
			method = "drawSlot",
			at = @At("TAIL")
	)
	void onPostSlotDraw(Slot slotIn, CallbackInfo ci) {
		MinecraftForge.EVENT_BUS.post(new InventorySlotDrawEvent.Post(slotIn));
	}
}
