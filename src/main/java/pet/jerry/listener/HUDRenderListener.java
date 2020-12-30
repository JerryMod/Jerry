package pet.jerry.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pet.jerry.Jerry;
import pet.jerry.core.Toggleable;
import pet.jerry.feature.Feature;
import pet.jerry.hud.Dimension;
import pet.jerry.hud.HUDEditScreen;
import pet.jerry.hud.HUDElement;
import pet.jerry.hud.Position;

@SideOnly(Side.CLIENT)
public class HUDRenderListener {
	@SubscribeEvent
	public void renderGameOverlay(RenderGameOverlayEvent.Text event) {
		if(Minecraft.getMinecraft().currentScreen == null) {
			for (Feature feature : Jerry.INSTANCE.getFeatureRegistry().getFeatures()) {
				if(!(feature instanceof HUDElement))
					continue;

				if(feature instanceof Toggleable && !((Toggleable) feature).isEnabled())
					continue;

				HUDElement element = (HUDElement) feature;

				Dimension dimension = element.getDimension();
				Position pos = element.getPosition().toAbsolute(dimension);
				element.draw(pos.getX(), pos.getY());
			}
		}
	}

	@SubscribeEvent
	void renderHunger(RenderGameOverlayEvent event) {
		if(event.type.equals(RenderGameOverlayEvent.ElementType.FOOD)) {
			event.setCanceled(true);
		}
	}
}
