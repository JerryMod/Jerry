package pet.jerry.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import pet.jerry.hud.HUDEditScreen;
import pet.jerry.screen.FeaturesList;
import pet.jerry.screen.FeaturesScreen;

@SideOnly(Side.CLIENT)
public class KeyBindingListener {
	private final KeyBinding hudEditKeybind = new KeyBinding("key.jerry.hud", Keyboard.KEY_GRAVE, "key.category.jerry");
	private final KeyBinding featureScreenKeyBind = new KeyBinding("key.jerry.menu", Keyboard.KEY_NONE, "key.category.jerry");

	public KeyBindingListener() {
		ClientRegistry.registerKeyBinding(hudEditKeybind);
		ClientRegistry.registerKeyBinding(featureScreenKeyBind);
	}

	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event) {
		if(hudEditKeybind.isPressed()) {
			Minecraft.getMinecraft().displayGuiScreen(new HUDEditScreen());
		} else if (featureScreenKeyBind.isPressed()) {
			Minecraft.getMinecraft().displayGuiScreen(new FeaturesScreen());
		}
	}
}
