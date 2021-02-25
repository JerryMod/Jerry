package pet.jerry.value;

import pet.jerry.core.Named;
import pet.jerry.util.ChromaUtil;
import pet.jerry.util.GLColour;
import pet.jerry.value.number.IntegerValue;

public class NamedColour extends DefaultSaveableContainer implements Named {
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
			return ChromaUtil.getChromaColour();
		}
		if (!allowAlpha)
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
		GLColour.applyHex(this.actualColour.getValue());
	}

	public void applyDarkerGL() {
		GLColour.applyHex(this.darker());
	}

	public int darker() {
		return (actualColour.getValue() & 16579836) >> 2 | actualColour.getValue() & -16777216;
	}

	public BooleanValue getChroma() {
		return chroma;
	}

	public int getActualColour() {
		return actualColour.getValue();
	}
}
