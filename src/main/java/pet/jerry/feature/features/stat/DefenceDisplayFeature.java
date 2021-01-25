package pet.jerry.feature.features.stat;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.hud.TextHUDElement;
import pet.jerry.hud.icon.IconLocation;
import pet.jerry.hud.icon.ImageIcon;

import java.util.Collections;

@FeatureInfo(id = "defence_display", name = "Defence Display")
public class DefenceDisplayFeature extends TextHUDElement {
	public DefenceDisplayFeature() {
		super((skyBlock) -> Collections.singletonList(skyBlock.getPlayingUser().getDefence() + ""));
		this.getTextColour().setColour(0x00FF33);
		this.setIcon(new ImageIcon(new ResourceLocation("jerry:shield.png"), 32, 8, IconLocation.AFTER));
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	void onActionBarUpdate(ClientChatReceivedEvent event) {
		if(event.type == 2 && this.isEnabled()) {
			event.message = new ChatComponentText(
					event.message.getFormattedText()
							.replaceAll("\247.\\d+❈ Defense", "")
							.trim());
		}
	}
}
