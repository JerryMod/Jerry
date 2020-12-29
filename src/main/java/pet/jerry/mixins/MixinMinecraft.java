package pet.jerry.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pet.jerry.Jerry;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Shadow
	public EntityPlayerSP thePlayer;

	@Inject(method = "displayGuiScreen", at = @At("HEAD"))
	public void pleaseDontWipeMe(GuiScreen guiScreenIn, CallbackInfo ci) {
		if (Jerry.INSTANCE.getFeatureRegistry().isEnabled("anti_wipe")
				&& this.thePlayer != null
				&& guiScreenIn == null
				&& this.thePlayer.getHealth() <= 0.0F) {
			System.err.println("======== JERRY DID THIS ========");
			System.err.println("A vanilla death screen was detected");
			System.err.println("Your client has exited to prevent loss of items.");
			System.exit(69); // nice
		}
	}
}
