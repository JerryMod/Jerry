package pet.jerry.hud;

import java.util.List;
import java.util.function.Supplier;

public class TextHUDElement extends AbstractHUDElement {
	private final Supplier<List<String>> supplier;
	private final NamedColour textColour = new NamedColour("Colour", "colour");

	public TextHUDElement(Position position, Supplier<List<String>> supplier) {
		super(position);
		this.supplier = supplier;
		this.getContainer().add(textColour);
	}

	public TextHUDElement(Supplier<List<String>> supplier) {
		this(new Position(0f, 0f), supplier);
	}

	protected NamedColour getTextColour() {
		return textColour;
	}

	@Override
	public void draw(float x, float y) {
		List<String> textToDraw = supplier.get();
		for (int i = 0; i < textToDraw.size(); i++) {
			String s = textToDraw.get(i);
			mc.fontRendererObj.drawStringWithShadow(s, x + 2,
					(y + i * (mc.fontRendererObj.FONT_HEIGHT + 2)) + 2, textColour.toHex());
		}
	}

	@Override
	public Dimension getDimension() {
		List<String> textToDraw = supplier.get();
		int maxWidth = 0;
		for (String s : textToDraw) {
			int w = mc.fontRendererObj.getStringWidth(s);
			if (w > maxWidth)
				maxWidth = w;
		}
		return new Dimension(maxWidth + 4, (mc.fontRendererObj.FONT_HEIGHT + 2) * textToDraw.size());
	}
}
