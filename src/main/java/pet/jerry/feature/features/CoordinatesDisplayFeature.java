package pet.jerry.feature.features;

import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.hud.TextHUDElement;

import java.util.Arrays;

@FeatureInfo(id = "coordinates", name = "Coordinates", category = FeatureCategory.INFORMATION)
public class CoordinatesDisplayFeature extends TextHUDElement {
	public CoordinatesDisplayFeature() {
		super((skyBlock) -> Arrays.asList(
				String.format("X: %.1f", mc.thePlayer.posX),
				String.format("Y: %.1f", mc.thePlayer.posY),
				String.format("Z: %.1f", mc.thePlayer.posZ))
		);
	}
}
