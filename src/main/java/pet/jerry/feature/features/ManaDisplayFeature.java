package pet.jerry.feature.features;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.hud.position.Position;
import pet.jerry.hud.TextHUDElement;

import java.util.Collections;

@FeatureInfo(id = "mana_display", name = "Mana Display", category = FeatureCategory.INFORMATION)
public class ManaDisplayFeature extends TextHUDElement {
	public ManaDisplayFeature() {
		super((skyBlock) -> Collections.singletonList(
				skyBlock.getPlayingUser().getMana() + " / " + skyBlock.getPlayingUser().getMaxMana()
		), new Position(0.1f, 0.1f));
		this.getTextColour().setColour(0x0033FF);
	}


	@SubscribeEvent(priority = EventPriority.LOW)
	void onActionBarUpdate(ClientChatReceivedEvent event) {
		if(event.type == 2 && this.isEnabled()) {
			event.message = new ChatComponentText(
					event.message.getFormattedText()
							.replaceAll("\247.\\d+/\\d+✎ Mana", "")
							.trim());
		}
	}
}
