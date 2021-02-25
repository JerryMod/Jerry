package pet.jerry.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import pet.jerry.Jerry;
import pet.jerry.core.Toggleable;
import pet.jerry.data.base.SkyBlock;
import pet.jerry.data.mock.MockSkyBlock;
import pet.jerry.hud.HUDEditScreen;
import pet.jerry.hud.HUDElement;
import pet.jerry.hud.group.RenderGroup;
import pet.jerry.hud.position.Dimension;
import pet.jerry.hud.position.Position;

@SideOnly(Side.CLIENT)
public class HUDRenderListener {
	private final MockSkyBlock mockSkyBlock = new MockSkyBlock();

	@SubscribeEvent
	public void renderGameOverlay(RenderGameOverlayEvent.Text event) {
		if (!(Minecraft.getMinecraft().currentScreen instanceof HUDEditScreen)) {

			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution resolution = new ScaledResolution(mc);
			SkyBlock skyBlock = Jerry.INSTANCE.getSkyBlock() == null ? mockSkyBlock : Jerry.INSTANCE.getSkyBlock();

			for (RenderGroup renderGroup : Jerry.INSTANCE.getRenderGroupRegistry().getItems()) {
				if (!renderGroup.canRender(skyBlock))
					continue;

				for (HUDElement element : renderGroup.getMembers()) {
					if (element instanceof Toggleable && !((Toggleable) element).isEnabled())
						continue;

					Dimension dimension = element.getDimension(skyBlock);
					Position pos = element.getPosition().toAbsolute(dimension);
					int x = Math.round(pos.getX());
					int y = Math.round(pos.getY());

					int w = Math.round(dimension.getWidth());
					int h = Math.round(dimension.getHeight());
					int scale = resolution.getScaleFactor();
					GlStateManager.pushMatrix();
					GlStateManager.translate(pos.getX(), pos.getY(), 1);
					GL11.glScissor(x * scale, mc.displayHeight - y * scale - h * scale, Math.max(0, w * scale), Math.max(0, h * scale));
					GlStateManager.color(1, 1, 1, 1);
					GL11.glEnable(GL11.GL_SCISSOR_TEST);
					element.draw(skyBlock);
					GL11.glDisable(GL11.GL_SCISSOR_TEST);
					GlStateManager.popMatrix();
				}
			}
		}
	}

	@SubscribeEvent
	void overlayRendering(RenderGameOverlayEvent.Pre event) {
		if (event == null || event.type == null) return; // ok

		if (event.type.equals(RenderGameOverlayEvent.ElementType.FOOD)) {
			event.setCanceled(true);
		} else if (event.type.equals(RenderGameOverlayEvent.ElementType.ARMOR)) {
			event.setCanceled(true);
		}
	}
}
