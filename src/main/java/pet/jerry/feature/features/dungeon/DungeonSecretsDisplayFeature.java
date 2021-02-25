package pet.jerry.feature.features.dungeon;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.data.base.SkyBlock;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.hud.TextHUDElement;
import pet.jerry.hud.group.DungeonRenderGroup;
import pet.jerry.hud.icon.ImageIcon;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FeatureInfo(name = "Dungeon Secrets Display", id = "dungeon_secrets_display", category = FeatureCategory.DUNGEON)
public class DungeonSecretsDisplayFeature extends TextHUDElement {
	private static final Pattern SECRETS_PATTERN = Pattern.compile("(\\d+)/(\\d+) Secrets");
	private int currentSecrets = -1;
	private int currentMaxSecrets = -1;

	public DungeonSecretsDisplayFeature() {
		super(skyBlock -> Collections.emptyList(), DungeonRenderGroup.get());
		this.setIcon(new ImageIcon(new ResourceLocation("jerry:magnifying-glass.png")));
		this.getTextColour().setColour(0xFFB139);
	}

	@SubscribeEvent
	void onActionBar(ClientChatReceivedEvent event) {
		if(event.type == 2 && this.isEnabled()) {
			String actionBarMsg = event.message.getUnformattedText()
					.replaceAll("\247.", "");
			Matcher matcher = SECRETS_PATTERN.matcher(actionBarMsg);
			if(matcher.find()) {
				try {
					this.currentSecrets = Integer.parseInt(matcher.group(1));
					this.currentMaxSecrets = Integer.parseInt(matcher.group(2));
				} catch (Exception e) {
					e.printStackTrace();
				}
				event.message
						= new ChatComponentText(event.message.getFormattedText().replaceAll("\247.\\d+/\\d+.*Secrets", ""));
			} else {
				this.currentSecrets = -1;
				this.currentMaxSecrets = -1;
				System.out.println("didnt match");
			}
		}
	}

	@Override
	protected List<String> getDisplayedText(SkyBlock skyBlock) {
		if(currentSecrets > -1 && currentMaxSecrets > -1) {
			return Collections.singletonList(String.format("%d/%d", currentSecrets, currentMaxSecrets));
		}
		return Collections.singletonList("None");
	}
}
