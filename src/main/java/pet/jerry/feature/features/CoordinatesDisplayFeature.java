package pet.jerry.feature.features;

import pet.jerry.annotation.FeatureInfo;
import pet.jerry.hud.Position;
import pet.jerry.hud.TextHUDElement;

import java.util.Arrays;

@FeatureInfo(id = "coordinates", name = "Coordinates")
public class CoordinatesDisplayFeature extends TextHUDElement {
	public CoordinatesDisplayFeature() {
		super(new Position(0f, 0f), (skyBlock) -> Arrays.asList(
				String.format("X: %.1f", mc.thePlayer.posX),
				String.format("Y: %.1f", mc.thePlayer.posY),
				String.format("Z: %.1f", mc.thePlayer.posZ))
		);
	}
}
