package pet.jerry.feature.features.dungeon;

import net.minecraft.util.ResourceLocation;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.hud.TextHUDElement;
import pet.jerry.hud.group.DungeonRenderGroup;
import pet.jerry.hud.icon.IconLocation;
import pet.jerry.hud.icon.ImageIcon;

import java.util.Collections;

@FeatureInfo(id = "dungeon_deaths", name = "Dungeon Deaths Counter")
public class DungeonDeathsCounter extends TextHUDElement {
	private static final ResourceLocation SKULL_ICON = new ResourceLocation("jerry:skull.png");

	public DungeonDeathsCounter() {
		super(skyBlock -> Collections.singletonList(skyBlock.getCurrentDungeon().getTotalDeaths() + ""), DungeonRenderGroup.get());
		this.getTextColour().setColour(0xFF3333);
		this.setIcon(new ImageIcon(SKULL_ICON, 32, 8, IconLocation.BEFORE));
	}
}
