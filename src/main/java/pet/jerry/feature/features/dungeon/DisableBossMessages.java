package pet.jerry.feature.features.dungeon;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.value.BooleanValue;

@FeatureInfo(name = "Disable Boss Messages", id = "boss_silencer")
public class DisableBossMessages extends AbstractToggleableFeature {
	private final BooleanValue disableThornCrowd = new BooleanValue("Disable Thorn Crowd", "thorn_crowd", true);

	public DisableBossMessages() {
		this.getContainer().add(disableThornCrowd);
	}

	@SubscribeEvent
	void onChat(ClientChatReceivedEvent event) {
		if(this.isEnabled()) {
			if(event.message.getUnformattedText().startsWith("[BOSS]")) {
				event.setCanceled(true);
			} else if(event.message.getUnformattedText().startsWith("[CROWD]")) {
				event.setCanceled(disableThornCrowd.getValue());
			}
		}
	}
}