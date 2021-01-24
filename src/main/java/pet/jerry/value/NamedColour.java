package pet.jerry.value;

import net.minecraft.client.renderer.GlStateManager;
import pet.jerry.core.Named;
import pet.jerry.value.BooleanValue;
import pet.jerry.value.IntegerValue;
import pet.jerry.value.SaveableContainer;

import java.awt.*;

public class NamedColour extends SaveableContainer implements Named {
	private final BooleanValue chroma = new BooleanValue("Chroma", "chroma", false);
	private final IntegerValue actualColour = new IntegerValue("Colour", "colour", 0xFFFFFF);
	private final boolean allowAlpha;

	public NamedColour(String name, String id) {
		this(name, id, false);
	}

	public NamedColour(String name, String id, boolean allowAlpha) {
		super(name, id);
		this.allowAlpha = allowAlpha;
		this.add(chroma);
		this.add(actualColour);
	}

	public int toHex() {
		if (chroma.getValue()) {
			return 0;
		}
		if(!allowAlpha)
			return this.toHex(255);
		return actualColour.getValue();
	}

	public int toHex(float alpha) {
		return this.toHex(Math.round(alpha * 255));
	}

	public int toHex(int alpha) {
		return ((alpha & 0xFF) << 24) | actualColour.getValue();
	}

	public void setColour(int hex) {
		this.actualColour.setValue(hex);
	}

	public void setChroma(boolean chroma) {
		this.chroma.setValue(chroma);
	}

	public boolean isAlphaAllowed() {
		return allowAlpha;
	}

	public void applyGL() {
		this.applyIntAsGLColours(this.actualColour.getValue());
	}

	public void applyDarkerGL() {
		this.applyIntAsGLColours(this.darker());
	}

	public int darker() {
		return (actualColour.getValue() & 16579836) >> 2 | actualColour.getValue() & -16777216;
	}

	private void applyIntAsGLColours(int col) {
		GlStateManager.color(((col >> 16) & 0xFF) / 255f,
				((col >> 8) & 0xFF) / 255f,
				(col & 0xFF) / 255f,
				this.allowAlpha ? ((col >> 24) & 0xFF) / 255f : 1f);
	}
}
