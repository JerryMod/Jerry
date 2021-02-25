package pet.jerry.value;

import pet.jerry.hud.StringRenderingMethod;

public final class GlobalSettingsContainer extends DefaultSaveableContainer {
	private static final GlobalSettingsContainer INSTANCE = new GlobalSettingsContainer();

	private final BooleanValue devMode = new BooleanValue("Developer Mode", "dev");
	private final EnumValue<StringRenderingMethod> stringRenderMethod
			= new EnumValue<>("String Rendering Method", "string_rendering_method", StringRenderingMethod.OUTLINE);

	private GlobalSettingsContainer() {
		super("Global Settings", "global");
		this.add(devMode, stringRenderMethod);
	}

	public static GlobalSettingsContainer get() {
		return INSTANCE;
	}

	public StringRenderingMethod getStringRenderMethod() {
		return stringRenderMethod.getValue();
	}
}
