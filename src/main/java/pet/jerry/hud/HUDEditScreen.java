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
import pet.jerry.feature.AbstractFeature;
import pet.jerry.feature.Feature;
import pet.jerry.hud.position.Dimension;
import pet.jerry.hud.position.Position;
import pet.jerry.screen.configure.ContainerConfigurationScreen;

import java.io.IOException;

public class HUDEditScreen extends GuiScreen {
	private final MockSkyBlock mockSkyBlock = new MockSkyBlock();

	private HUDElement hoveredElement;
	private float prevMouseX, prevMouseY;
	private boolean isDragging;
	private final GuiScreen parent;

	public HUDEditScreen() {
		this(null);
	}

	public HUDEditScreen(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		boolean anyHovered = false;
		for (Feature feature : Jerry.INSTANCE.getFeatureRegistry().getItems()) {
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
			GlStateManager.translate(pos.getX(), pos.getY(), 0);
			GlStateManager.enableBlend();
			GlStateManager.enableAlpha();
			drawRectFloats(0, 0, dimension.getWidth(), dimension.getHeight(),
					hovered ? 0xCC00FF00 : 0xCCAAAAAA);

			int x = Math.round(pos.getX());
			int y = Math.round(pos.getY());

			int w = Math.round(dimension.getWidth());
			int h = Math.round(dimension.getHeight());

			ScaledResolution resolution = new ScaledResolution(mc);
			int scale = resolution.getScaleFactor();
			GL11.glScissor(x * scale, mc.displayHeight - y * scale - h * scale, Math.max(0, w * scale), Math.max(0, h * scale));
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			element.draw(mockSkyBlock);
			GL11.glDisable(GL11.GL_SCISSOR_TEST);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
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

		this.drawCenteredString(mc.fontRendererObj, "Shift+Click an item to edit its settings", this.width / 2, 4, 0xffffff);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(hoveredElement != null) {
			if ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
				if(hoveredElement instanceof AbstractFeature) {
					mc.displayGuiScreen(new ContainerConfigurationScreen(this, (AbstractFeature) hoveredElement));
					isDragging = false;
					hoveredElement = null;
				}
			} else {
				Position position = hoveredElement.getPosition().toAbsolute(hoveredElement.getDimension(mockSkyBlock));
				prevMouseX = mouseX - position.getX();
				prevMouseY = mouseY - position.getY();
				isDragging = true;
				mc.thePlayer.playSound("random.click", 1f, 0.8f);
			}
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
