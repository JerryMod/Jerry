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
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.command.CataCommand;
import pet.jerry.command.JerryCommand;
import pet.jerry.data.DefaultSkyBlock;
import pet.jerry.data.ActionBarListener;
import pet.jerry.data.PlayerListListener;
import pet.jerry.data.base.SkyBlock;
import pet.jerry.data.mock.MockSkyBlock;
import pet.jerry.event.SkyBlockConnectionEvent;
import pet.jerry.feature.FeatureRegistry;
import pet.jerry.hud.group.RenderGroupRegistry;
import pet.jerry.listener.HUDRenderListener;
import pet.jerry.listener.KeyBindingListener;
import pet.jerry.data.ScoreboardListener;
import pet.jerry.util.RomanNumerals;
import pet.jerry.value.ConfigRegistry;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Mod(
		modid = Jerry.MOD_ID,
		name = Jerry.MOD_NAME,
		version = Jerry.VERSION
)
public class Jerry {
	public static final String MOD_ID = "jerry";
	public static final String MOD_NAME = "Jerry";
	public static final String VERSION = "@VERSION@";

	@Mod.Instance(MOD_ID)
	public static Jerry INSTANCE;

	private FeatureRegistry featureRegistry;
	private ConfigRegistry configRegistry;
	private RenderGroupRegistry renderGroupRegistry;

	private final List<Object> skyBlockListeners = Collections.unmodifiableList(Arrays.asList(
			new ActionBarListener(),
			//new HUDRenderListener(),
			new PlayerListListener()
	));

	private SkyBlock skyBlock = null;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		this.renderGroupRegistry = new RenderGroupRegistry();
		this.configRegistry = new ConfigRegistry(event.getModConfigurationDirectory());
		this.featureRegistry = new FeatureRegistry();

		ClientCommandHandler.instance.registerCommand(new JerryCommand());
		ClientCommandHandler.instance.registerCommand(new CataCommand());

		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(featureRegistry);
		MinecraftForge.EVENT_BUS.register(new ScoreboardListener());
		MinecraftForge.EVENT_BUS.register(new KeyBindingListener());
		MinecraftForge.EVENT_BUS.register(new HUDRenderListener());

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

		try {
			this.configRegistry.load();
			this.configRegistry.save();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				this.configRegistry.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
	}

	public SkyBlock getSkyBlock() {
		return skyBlock;
	}

	public FeatureRegistry getFeatureRegistry() {
		return featureRegistry;
	}

	public ConfigRegistry getConfigRegistry() {
		return configRegistry;
	}

	public RenderGroupRegistry getRenderGroupRegistry() {
		return renderGroupRegistry;
	}

	@SubscribeEvent
	void onEnterSkyBlock(SkyBlockConnectionEvent.Enter event) {
		System.out.println("Detected entering SkyBlock.");
		this.skyBlock = new DefaultSkyBlock();
		for (Object o : skyBlockListeners) {
			MinecraftForge.EVENT_BUS.register(o);
		}
	}

	@SubscribeEvent
	void onExitSkyBlock(SkyBlockConnectionEvent.Exit event) {
		for (Object o : skyBlockListeners) {
			MinecraftForge.EVENT_BUS.unregister(o);
		}
		this.skyBlock = new MockSkyBlock();
	}
}
