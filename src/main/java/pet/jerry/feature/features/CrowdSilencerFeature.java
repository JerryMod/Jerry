package pet.jerry.feature.features;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.feature.AbstractToggleableFeature;

@FeatureInfo(name = "Disable Thorn Crowd Messages", id = "crowd_silencer")
public class CrowdSilencerFeature extends AbstractToggleableFeature {
	@SubscribeEvent
	void onChat(ClientChatReceivedEvent event) {
		if(this.isEnabled() && event.message.getUnformattedText().startsWith("[CROWD]")) {
			event.setCanceled(true);
		}
	}
}