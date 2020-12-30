package pet.jerry.hud;

import pet.jerry.core.Named;
import pet.jerry.value.BooleanValue;
import pet.jerry.value.IntegerValue;
import pet.jerry.value.SaveableContainer;

public class NamedColour extends SaveableContainer implements Named {
	private final BooleanValue chroma = new BooleanValue("Chroma", "chroma", false);
	private final IntegerValue actualColour = new IntegerValue("Colour", "colour", 0xFFFFFF);

	public NamedColour(String name, String id) {
		super(name, id);
		this.add(chroma);
		this.add(actualColour);
	}

	public int toHex() {
		if (chroma.getValue()) {
			return 0;
		}
		return actualColour.getValue();
	}

	public void setColour(int hex) {
		this.actualColour.setValue(hex);
	}

	public void setChroma(boolean chroma) {
		this.chroma.setValue(chroma);
	}
}
