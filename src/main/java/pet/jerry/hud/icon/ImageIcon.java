package pet.jerry.hud.icon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import pet.jerry.hud.position.Dimension;

public class ImageIcon implements Icon {
	private final ResourceLocation iconImage;
	private final int imgSize;
	private final int renderSize;
	private final IconLocation location;

	public ImageIcon(ResourceLocation iconImage, int imgSize, int renderSize) {
		this(iconImage, imgSize, renderSize, IconLocation.BEFORE);
	}

	public ImageIcon(ResourceLocation iconImage, int imgSize, int renderSize, IconLocation location) {
		this.iconImage = iconImage;
		this.imgSize = imgSize;
		this.renderSize = renderSize;
		this.location = location;
	}

	@Override
	public void drawIcon(float x, float y) {
		float scaleFactor = (float) renderSize / (float) imgSize;
		GlStateManager.pushMatrix();
		GlStateManager.scale(scaleFactor, scaleFactor, scaleFactor);
		Minecraft.getMinecraft().getTextureManager().bindTexture(iconImage);
		Gui.drawModalRectWithCustomSizedTexture(Math.round(x / scaleFactor), Math.round(y / scaleFactor),
				0, 0, imgSize, imgSize, imgSize, imgSize);
		GlStateManager.popMatrix();
	}

	@Override
	public Dimension augmentDimension(Dimension dimension) {
		dimension.setWidth(dimension.getWidth() + this.renderSize + 2);
		return dimension;
	}

	@Override
	public Dimension getDimension() {
		return new Dimension(renderSize, renderSize);
	}

	@Override
	public IconLocation getIconLocation() {
		return location;
	}
}
