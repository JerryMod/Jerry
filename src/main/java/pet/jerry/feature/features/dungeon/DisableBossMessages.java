package pet.jerry.feature.features.dungeon;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.value.annotation.Value;

@FeatureInfo(name = "Disable Boss Messages", id = "boss_silencer", category = FeatureCategory.DUNGEON)
public class DisableBossMessages extends AbstractToggleableFeature {
	@Value(name = "Disable Thorn Crowd", id = "thorn_crowd")
	private boolean disableThornCrowd = true;

	@SubscribeEvent
	void onChat(ClientChatReceivedEvent event) {
		if(this.isEnabled()) {
			if(event.message.getUnformattedText().startsWith("[BOSS]")) {
				event.setCanceled(true);
			} else if(event.message.getUnformattedText().startsWith("[CROWD]")) {
				event.setCanceled(disableThornCrowd);
			}
		}
	}
}