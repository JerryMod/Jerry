package pet.jerry.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pet.jerry.event.ItemDropEvent;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
	@Inject(method = "dropOneItem", at = @At("HEAD"), cancellable = true)
	void onDropItem(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
		ItemDropEvent dropEvent = new ItemDropEvent();
		MinecraftForge.EVENT_BUS.post(dropEvent);

		if(dropEvent.isCanceled()) {
			cir.setReturnValue(null);
		}
	}
}
