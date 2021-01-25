package pet.jerry.feature.features;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.hud.position.Position;
import pet.jerry.hud.TextHUDElement;

import java.util.Collections;

@FeatureInfo(id = "health_display", name = "Health Display")
public class HealthDisplayFeature extends TextHUDElement {
	public HealthDisplayFeature() {
		super((skyBlock) -> Collections.singletonList(
				skyBlock.getPlayingUser().getHealth()
						+ " / " + skyBlock.getPlayingUser().getMaxHealth()
		), new Position(0.1f, 0.2f));
		this.getTextColour().setColour(0xFF0000);
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	void onActionBarUpdate(ClientChatReceivedEvent event) {
		if(event.type == 2 && this.isEnabled()) {
			event.message = new ChatComponentText(
					event.message.getFormattedText()
							.replaceAll("\247.\\d+/\\d+❤", "")
							.trim());
		}
	}
}
