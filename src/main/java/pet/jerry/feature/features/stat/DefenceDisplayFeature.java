package pet.jerry.feature.features.stat;

import net.minecraft.util.ResourceLocation;
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
}
