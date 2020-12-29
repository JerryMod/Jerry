package pet.jerry.hud;

import java.util.List;
import java.util.function.Supplier;

public class TextHUDElement extends AbstractHUDElement {
	private final Supplier<List<String>> supplier;

	public TextHUDElement(Position position, Supplier<List<String>> supplier) {
		super(position);
		this.supplier = supplier;
	}

	@Override
	public void draw(float x, float y) {
		List<String> textToDraw = supplier.get();
		for (int i = 0; i < textToDraw.size(); i++) {
			String s = textToDraw.get(i);
			mc.fontRendererObj.drawStringWithShadow(s, x,
					y + i * (mc.fontRendererObj.FONT_HEIGHT + 2), 0xffffff);
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
		return new Dimension(maxWidth, (mc.fontRendererObj.FONT_HEIGHT + 2) * textToDraw.size());
	}
}
