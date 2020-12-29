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

@SideOnly(Side.CLIENT)
public class KeyBindingListener {
	private final KeyBinding hudEditKeybind = new KeyBinding("key.jerry.hud", Keyboard.KEY_GRAVE, "key.category.jerry");

	public KeyBindingListener() {
		ClientRegistry.registerKeyBinding(hudEditKeybind);
	}

	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event) {
		if(hudEditKeybind.isPressed()) {
			Minecraft.getMinecraft().displayGuiScreen(new HUDEditScreen());
		}
	}
}
