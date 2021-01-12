package pet.jerry.hud;

import pet.jerry.data.base.SkyBlock;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class TextHUDElement extends AbstractHUDElement {
	private final Function<SkyBlock, List<String>> supplier;
	private final NamedColour textColour = new NamedColour("Colour", "colour");

	public TextHUDElement(Position position, Function<SkyBlock, List<String>> supplier) {
		super(position);
		this.supplier = supplier;
		this.getContainer().add(textColour);
	}

	public TextHUDElement(Function<SkyBlock, List<String>> supplier) {
		this(new Position(0f, 0f), supplier);
	}

	protected List<String> getDisplayedText(SkyBlock skyBlock) {
		return supplier.apply(skyBlock);
	}

	protected NamedColour getTextColour() {
		return textColour;
	}

	@Override
	public void draw(float x, float y, SkyBlock skyBlock) {
		List<String> textToDraw = this.getDisplayedText(skyBlock);
		for (int i = 0; i < textToDraw.size(); i++) {
			String s = textToDraw.get(i);
			this.drawString(s, x + 2,
					(y + i * (mc.fontRendererObj.FONT_HEIGHT + 2)) + 2, textColour.toHex());
		}
	}

	@Override
	public Dimension getDimension(SkyBlock skyBlock) {
		List<String> textToDraw = this.getDisplayedText(skyBlock);
		int maxWidth = 0;
		for (String s : textToDraw) {
			int w = mc.fontRendererObj.getStringWidth(s);
			if (w > maxWidth)
				maxWidth = w;
		}
		return new Dimension(maxWidth + 4, (mc.fontRendererObj.FONT_HEIGHT + 2) * textToDraw.size());
	}
}
