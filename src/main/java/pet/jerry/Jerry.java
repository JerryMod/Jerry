package pet.jerry;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pet.jerry.command.CataCommand;
import pet.jerry.command.JerryCommand;
import pet.jerry.feature.FeatureRegistry;
import pet.jerry.feature.features.AntiWipeFeature;
import pet.jerry.feature.features.CoordinatesDisplayFeature;
import pet.jerry.listener.HUDRenderListener;
import pet.jerry.listener.KeyBindingListener;
import pet.jerry.listener.ScoreboardListener;

import java.lang.reflect.Field;
import java.net.Proxy;
import java.util.UUID;

@Mod(
		modid = Jerry.MOD_ID,
		name = Jerry.MOD_NAME,
		version = Jerry.VERSION
)
public class Jerry {
	public static final String MOD_ID = "jerry";
	public static final String MOD_NAME = "Jerry";
	public static final String VERSION = "1.0-SNAPSHOT";

	@Mod.Instance(MOD_ID)
	public static Jerry INSTANCE;

	private final FeatureRegistry featureRegistry = new FeatureRegistry();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new JerryCommand());
		ClientCommandHandler.instance.registerCommand(new CataCommand());

		featureRegistry.registerFeature(new AntiWipeFeature());
		featureRegistry.registerFeature(new CoordinatesDisplayFeature());

		MinecraftForge.EVENT_BUS.register(new ScoreboardListener());
		MinecraftForge.EVENT_BUS.register(new HUDRenderListener());
		MinecraftForge.EVENT_BUS.register(new KeyBindingListener());

		if (System.getenv().containsKey("MINECRAFT_USERNAME")
				&& System.getenv().containsKey("MINECRAFT_PASSWORD")) {
			String username = System.getenv("MINECRAFT_USERNAME");
			String password = System.getenv("MINECRAFT_PASSWORD");

			Field sessionField = null;
			for (Field field : Minecraft.class.getDeclaredFields()) {
				if (field.getType().equals(Session.class)) {
					sessionField = field;
					break;
				}
			}

			if (sessionField != null) {
				try {
					YggdrasilUserAuthentication ua = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString())
							.createUserAuthentication(Agent.MINECRAFT);
					ua.setUsername(username);
					ua.setPassword(password);
					ua.logIn();

					Session session = new Session(ua.getSelectedProfile().getName(),
							ua.getSelectedProfile().getId().toString().replaceAll("-", ""),
							ua.getAuthenticatedToken(), ua.getUserType().getName());

					sessionField.setAccessible(true);
					sessionField.set(Minecraft.getMinecraft(), session);
				} catch (AuthenticationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	public FeatureRegistry getFeatureRegistry() {
		return featureRegistry;
	}
}
