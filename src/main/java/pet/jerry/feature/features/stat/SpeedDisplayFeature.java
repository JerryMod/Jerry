package pet.jerry.feature.features.stat;

import net.minecraft.util.ResourceLocation;
import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.hud.TextHUDElement;
import pet.jerry.hud.icon.ImageIcon;

import java.util.Collections;

@FeatureInfo(id = "speed_display", name = "Speed Percentage Display", category = FeatureCategory.INFORMATION)
public class SpeedDisplayFeature extends TextHUDElement {
	public SpeedDisplayFeature() {
		super((skyBlock) -> Collections.singletonList(Math.round(mc.thePlayer.capabilities.getWalkSpeed() * 1000) + "%"));
		this.setIcon(new ImageIcon(new ResourceLocation("jerry:running.png")));
		this.getTextColour().setColour(0xFFFFFF);
	}
}
