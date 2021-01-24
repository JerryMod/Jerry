package pet.jerry.hud;

import pet.jerry.data.base.SkyBlock;
import pet.jerry.hud.group.DefaultRenderGroup;
import pet.jerry.hud.group.RenderGroup;
import pet.jerry.hud.icon.Icon;
import pet.jerry.hud.icon.IconLocation;
import pet.jerry.hud.position.Dimension;
import pet.jerry.hud.position.Position;
import pet.jerry.value.NamedColour;

import java.util.List;
import java.util.function.Function;

public class TextHUDElement extends AbstractHUDElement {
	private final Function<SkyBlock, List<String>> supplier;
	private final NamedColour textColour = new NamedColour("Colour", "colour");
	private Icon icon;

	public TextHUDElement(Function<SkyBlock, List<String>> supplier, Position position, RenderGroup group) {
		super(position, group);
		this.supplier = supplier;
		this.getContainer().add(textColour);
	}

	public TextHUDElement(Function<SkyBlock, List<String>> supplier, Position position) {
		this(supplier, position, DefaultRenderGroup.get());
	}

	public TextHUDElement(Function<SkyBlock, List<String>> supplier) {
		this(supplier, new Position(0f, 0f));
	}

	public TextHUDElement(Function<SkyBlock, List<String>> supplier, RenderGroup group) {
		this(supplier, new Position(0f, 0f), group);
	}

	protected void setIcon(Icon icon) {
		this.icon = icon;
	}

	protected List<String> getDisplayedText(SkyBlock skyBlock) {
		return supplier.apply(skyBlock);
	}

	protected NamedColour getTextColour() {
		return textColour;
	}

	@Override
	public void draw(float x, float y, SkyBlock skyBlock) {
		if (icon != null && icon.getIconLocation().equals(IconLocation.BEFORE)) {
			this.renderIcon(x + 1, y + 1);
			x += icon.getDimension().getWidth() + 2;
		}
		int maxWidth = -1;
		List<String> textToDraw = this.getDisplayedText(skyBlock);
		for (int i = 0; i < textToDraw.size(); i++) {
			String s = textToDraw.get(i);
			int width = mc.fontRendererObj.getStringWidth(s);
			if(width > maxWidth)
				maxWidth = width;
			this.drawString(s, x + 2,
					(y + i * (mc.fontRendererObj.FONT_HEIGHT + 2)) + 2, textColour.toHex());
		}

		if(icon != null && icon.getIconLocation().equals(IconLocation.AFTER)) {
			this.renderIcon(x + maxWidth + 2, y + 1);
		}
	}

	@Override
	public Dimension getDimension(SkyBlock skyBlock) {
		List<String> textToDraw = this.getDisplayedText(skyBlock);
		int maxWidth = 0;
		float height = (mc.fontRendererObj.FONT_HEIGHT + 2) * textToDraw.size();
		for (String s : textToDraw) {
			int w = mc.fontRendererObj.getStringWidth(s);
			if (w > maxWidth)
				maxWidth = w;
		}

		if (this.icon != null) {
			maxWidth += icon.getDimension().getWidth() + 2;
			if (icon.getDimension().getHeight() > height) {
				height = icon.getDimension().getHeight();
			}
		}
		return new Dimension(maxWidth + 2, height);
	}

	private void renderIcon(float x, float y) {
		if(this.getStringRenderingMethod().equals(StringRenderingMethod.SHADOW)) {
			this.renderIconShadow(x, y, this.textColour);
		} else if(this.getStringRenderingMethod().equals(StringRenderingMethod.OUTLINE)) {
			this.renderIconOutline(x, y, this.textColour);
		}
	}

	private void renderIconShadow(float x, float y, NamedColour colour) {
		colour.applyDarkerGL();
		icon.drawIcon(x + 1, y + 1);
		colour.applyGL();
		icon.drawIcon(x, y);
	}

	private void renderIconOutline(float x, float y, NamedColour colour) {
		colour.applyDarkerGL();
		icon.drawIcon(x, y - 1);
		icon.drawIcon(x, y + 1);
		icon.drawIcon(x - 1, y);
		icon.drawIcon(x + 1, y);
		colour.applyGL();
		icon.drawIcon(x, y);
	}
}
