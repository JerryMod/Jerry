package pet.jerry.mixins;

import net.minecraft.client.model.ModelBlaze;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pet.jerry.event.BlazeModelRenderEvent;

@Mixin(ModelBlaze.class)
public class MixinModelBlaze {
	@Inject(method = "render(Lnet/minecraft/entity/Entity;FFFFFF)V", at = @At("HEAD"))
	void onBlazeRender(Entity entityIn,
	                   float p_78088_2_,
	                   float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale, CallbackInfo ci) {
		if(entityIn instanceof EntityBlaze) {
			MinecraftForge.EVENT_BUS.post(new BlazeModelRenderEvent.Pre((EntityBlaze) entityIn));
		}
	}


	@Inject(method = "render(Lnet/minecraft/entity/Entity;FFFFFF)V", at = @At("TAIL"))
	void onBlazeRenderPost(Entity entityIn,
	                   float p_78088_2_,
	                   float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale, CallbackInfo ci) {
		if(entityIn instanceof EntityBlaze) {
			MinecraftForge.EVENT_BUS.post(new BlazeModelRenderEvent.Post((EntityBlaze) entityIn));
		}
	}
}
