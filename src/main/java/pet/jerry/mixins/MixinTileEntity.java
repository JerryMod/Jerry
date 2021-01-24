package pet.jerry.mixins;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pet.jerry.event.TileEntityPositionSetEvent;

@Mixin(TileEntity.class)
public class MixinTileEntity {
	@Inject(method = "setPos", at = @At("TAIL"))
	void onSetPos(BlockPos posIn, CallbackInfo ci) {
		MinecraftForge.EVENT_BUS.post(new TileEntityPositionSetEvent((TileEntity) (Object) this, posIn));
	}
}
