package pet.jerry.feature.features.bar;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.Jerry;

public class HealthBarFeature extends BarFeature {
	public HealthBarFeature() {
		super("Health",
				() -> Jerry.INSTANCE.getSkyBlock().getPlayingUser().getHealth(),
				() -> Jerry.INSTANCE.getSkyBlock().getPlayingUser().getMaxHealth());
		this.setBarColour(0xFFFF1111);
	}

	@SubscribeEvent
	void renderHealth(RenderGameOverlayEvent event) {
		if (event.type.equals(RenderGameOverlayEvent.ElementType.HEALTH)) {
			event.setCanceled(true);
		}
	}
}
