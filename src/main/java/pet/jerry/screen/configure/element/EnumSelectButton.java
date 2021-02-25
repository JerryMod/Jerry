package pet.jerry.screen.configure.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.apache.commons.lang3.StringUtils;
import pet.jerry.value.Value;

import java.util.Locale;

public class EnumSelectButton extends GuiButton {
	private final Class<Enum<?>> enumClass;
	private final Value<Enum<?>> value;

	public EnumSelectButton(int buttonId, Value<Enum<?>> value) {
		super(buttonId, 0, 0, 100, 20, value.getValue().name());
		this.value = value;
		Class<?> clazz = value.getValue().getClass();
		while (clazz.isAnonymousClass()) {
			clazz = clazz.getSuperclass();
		}
		this.enumClass = (Class<Enum<?>>) clazz;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			this.playPressSound(mc.getSoundHandler());
			this.rotateValue();
			return true;
		}
		return false;
	}

	private void rotateValue() {
		if (this.value.getValue().ordinal() >= this.enumClass.getEnumConstants().length - 1) {
			this.value.setValue(this.enumClass.getEnumConstants()[0]);
		} else {
			this.value.setValue(this.enumClass.getEnumConstants()[this.value.getValue().ordinal() + 1]);
		}

		this.displayString = StringUtils.capitalize(this.value.getValue().name().toLowerCase(Locale.ROOT));
	}
}
