package pet.jerry.feature.features.dungeon.map.render.custom;

import net.minecraft.client.Minecraft;
import pet.jerry.feature.features.dungeon.map.Room;
import pet.jerry.feature.features.dungeon.map.TileDecorations;

public class TextIconRenderer extends IconRenderer {
	private static final Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void drawIcon(Room room, float x, float y) {
		TileDecorations decoration = this.getRoomDecoration(room);
		if(decoration == TileDecorations.NONE)
			return;

		String label = "";
		switch (decoration) {
			case GREEN_CHECK:
				label = "\247a✓";
				break;
			case WHITE_CHECK:
				label = "✓";
				break;
			case PUZZLE_FAIL:
				label = "\2474x";
				break;
		}
		if(!label.isEmpty()) {
			if(this.shouldCentre()) {
				int width = mc.fontRendererObj.getStringWidth(label);
				mc.fontRendererObj.drawStringWithShadow(label,
						x - (width / 2f),y - (mc.fontRendererObj.FONT_HEIGHT / 2f),
						0xffffff);
			} else {
				mc.fontRendererObj.drawStringWithShadow(label,
						2, 2, 0xffffffff);
			}
		}
	}
}
