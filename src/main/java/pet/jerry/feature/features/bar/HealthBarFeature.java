package pet.jerry.feature.features.bar;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.Jerry;

public class HealthBarFeature extends BarFeature {
	public HealthBarFeature() {
		super("Health",
				(skyBlock) -> skyBlock.getPlayingUser().getHealth(),
				(skyBlock) -> skyBlock.getPlayingUser().getMaxHealth());
		this.setBarColour(0xFFFF1111);
	}

	@SubscribeEvent
	void renderHealth(RenderGameOverlayEvent.Pre event) {
		if(event == null || event.type == null) return;

		if (event.type.equals(RenderGameOverlayEvent.ElementType.HEALTH) && this.isEnabled()) {
			event.setCanceled(true);
		}
	}
}
