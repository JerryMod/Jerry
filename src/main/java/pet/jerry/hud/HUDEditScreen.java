package pet.jerry.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import pet.jerry.Jerry;
import pet.jerry.feature.Feature;

import java.io.IOException;

public class HUDEditScreen extends GuiScreen {
	private HUDElement hoveredElement;
	private float prevMouseX, prevMouseY;
	private boolean isDragging;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		boolean anyHovered = false;
		for (Feature feature : Jerry.INSTANCE.getFeatureRegistry().getFeatures()) {
			if(!(feature instanceof HUDElement))
				continue;

			HUDElement element = (HUDElement) feature;
			Dimension dimension = element.getDimension();
			Position pos = element.getPosition().toAbsolute(dimension);

			boolean hovered = mouseX > pos.getX()
							&& mouseX < pos.getX() + dimension.getWidth()
							&& mouseY > pos.getY()
							&& mouseY < pos.getY() + dimension.getHeight();

			if(hovered) {
				anyHovered = true;
				hoveredElement = element;
			}

			drawRectFloats(pos.getX(), pos.getY(), pos.getX() + dimension.getWidth(), pos.getY() + dimension.getHeight(),
					hovered ? 0xAA00FF00 : 0xCCAAAAAA);
			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(element.getName(), pos.getX() + 2, pos.getY() + 2, hovered ? 0xFFFFFF : 0x00FF00);
		}

		if(!anyHovered)
			hoveredElement = null;

		if(null != hoveredElement && isDragging) {
			Dimension dimension = hoveredElement.getDimension();
			Position position = hoveredElement.getPosition();
			ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
			float x = (((float) mouseX - prevMouseX) / (float) width) + ((dimension.getWidth() / (float) scaledResolution.getScaledWidth()) * position.getX());
			float y = (((float) mouseY - prevMouseY) / (float) height) + ((dimension.getHeight() / (float) scaledResolution.getScaledHeight()) * position.getY());
			position.setX(MathHelper.clamp_float(x, 0, 1));
			position.setY(MathHelper.clamp_float(y, 0, 1));
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(hoveredElement != null) {
			Position position = hoveredElement.getPosition().toAbsolute(hoveredElement.getDimension());
			prevMouseX = mouseX - position.getX();
			prevMouseY = mouseY - position.getY();
			isDragging = true;
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		if(null != hoveredElement) {
			isDragging = true;
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		hoveredElement = null;
		isDragging = false;
	}

	private void drawRectFloats(float left, float top, float right, float bottom, int color) {
		if (left < right) {
			float i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
			float j = top;
			top = bottom;
			bottom = j;
		}

		float alpha = (float) (color >> 24 & 255) / 255.0F;
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.color(red, green, blue, alpha);
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		worldrenderer.pos(left, bottom, 0.0D).endVertex();
		worldrenderer.pos(right, bottom, 0.0D).endVertex();
		worldrenderer.pos(right, top, 0.0D).endVertex();
		worldrenderer.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
}
