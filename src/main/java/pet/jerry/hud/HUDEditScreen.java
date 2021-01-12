package pet.jerry.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import pet.jerry.Jerry;
import pet.jerry.core.Toggleable;
import pet.jerry.data.mock.MockSkyBlock;
import pet.jerry.feature.Feature;

import java.io.IOException;

public class HUDEditScreen extends GuiScreen {
	private final MockSkyBlock mockSkyBlock = new MockSkyBlock();

	private HUDElement hoveredElement;
	private float prevMouseX, prevMouseY;
	private boolean isDragging;
	private GuiScreen parent;

	public HUDEditScreen() {
		this(null);
	}

	public HUDEditScreen(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		boolean anyHovered = false;
		for (Feature feature : Jerry.INSTANCE.getFeatureRegistry().getFeatures()) {
			if(!(feature instanceof HUDElement))
				continue;

			if(feature instanceof Toggleable && !((Toggleable) feature).isEnabled())
				continue;

			HUDElement element = (HUDElement) feature;
			Dimension dimension = element.getDimension(mockSkyBlock);
			Position pos = element.getPosition().toAbsolute(dimension);

			boolean hovered = mouseX > pos.getX()
							&& mouseX < pos.getX() + dimension.getWidth()
							&& mouseY > pos.getY()
							&& mouseY < pos.getY() + dimension.getHeight();

			if(hovered) {
				anyHovered = true;
				if(!isDragging)
					hoveredElement = element;
			}

			GlStateManager.pushMatrix();
			drawRectFloats(pos.getX(), pos.getY(), pos.getX() + dimension.getWidth(), pos.getY() + dimension.getHeight(),
					hovered ? 0xCC00FF00 : 0xCCAAAAAA);
			GlStateManager.popMatrix();
			element.draw(pos.getX(), pos.getY(), mockSkyBlock);
		}

		if(!anyHovered && !isDragging)
			hoveredElement = null;

		if(null != hoveredElement && isDragging) {
			Dimension dimension = hoveredElement.getDimension(mockSkyBlock);
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
			Position position = hoveredElement.getPosition().toAbsolute(hoveredElement.getDimension(mockSkyBlock));
			prevMouseX = mouseX - position.getX();
			prevMouseY = mouseY - position.getY();
			isDragging = true;
			mc.thePlayer.playSound("random.click", 1f, 0.8f);
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
		if(hoveredElement != null && isDragging)
			mc.thePlayer.playSound("random.click", 1f, 1.2f);
		hoveredElement = null;
		isDragging = false;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(parent);
			if(parent == null) {
				mc.setIngameFocus();
			}
		}
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
