package pet.jerry.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pet.jerry.Jerry;
import pet.jerry.core.Toggleable;
import pet.jerry.feature.Feature;
import pet.jerry.hud.group.RenderGroup;
import pet.jerry.hud.position.Dimension;
import pet.jerry.hud.HUDEditScreen;
import pet.jerry.hud.HUDElement;
import pet.jerry.hud.position.Position;
import pet.jerry.item.ItemUtils;

@SideOnly(Side.CLIENT)
public class HUDRenderListener {
	@SubscribeEvent
	public void renderGameOverlay(RenderGameOverlayEvent.Text event) {
		if(!(Minecraft.getMinecraft().currentScreen instanceof HUDEditScreen)) {
			for (RenderGroup renderGroup : Jerry.INSTANCE.getRenderGroupRegistry().getItems()) {
				if(!renderGroup.canRender(Jerry.INSTANCE.getSkyBlock()))
					continue;

				for (HUDElement element : renderGroup.getMembers()) {
					if(element instanceof Toggleable && !((Toggleable) element).isEnabled())
						continue;

					Dimension dimension = element.getDimension(Jerry.INSTANCE.getSkyBlock());
					Position pos = element.getPosition().toAbsolute(dimension);
					element.draw(pos.getX(), pos.getY(), Jerry.INSTANCE.getSkyBlock());
				}
			}
		}
	}

	@SubscribeEvent
	void overlayRendering(RenderGameOverlayEvent.Pre event) {
		if(event == null || event.type == null) return; // ok

		if(event.type.equals(RenderGameOverlayEvent.ElementType.FOOD)) {
			event.setCanceled(true);
		} else if(event.type.equals(RenderGameOverlayEvent.ElementType.ARMOR)) {
			event.setCanceled(true);
		}
	}
}
