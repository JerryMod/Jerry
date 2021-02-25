package pet.jerry.feature.features.dungeon.map.render.custom;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import pet.jerry.feature.features.dungeon.map.Room;
import pet.jerry.feature.features.dungeon.map.RoomType;
import pet.jerry.feature.features.dungeon.map.TileDecorations;
import pet.jerry.hud.icon.ImageIcon;

public class ImageIconRenderer extends IconRenderer {
	private static final ImageIcon CHECK_ICON = new ImageIcon(new ResourceLocation("jerry:map/check.png"));
	private static final ImageIcon QUESTION_ICON = new ImageIcon(new ResourceLocation("jerry:map/question-mark.png"));
	private static final ImageIcon X_ICON = new ImageIcon(new ResourceLocation("jerry:map/cancel-mark.png"));

	@Override
	public void drawIcon(Room room, float x, float y) {
		if (this.shouldCentre()) {
			x -= (CHECK_ICON.getDimension().getWidth() / 2);
			y -= (CHECK_ICON.getDimension().getHeight() / 2);
		}

		TileDecorations decoration = this.getRoomDecoration(room);
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		if (room.getType() == RoomType.UNDISCOVERED) {
			QUESTION_ICON.drawIcon(x, y);
		} else {
			switch (decoration) {
				case GREEN_CHECK:
					GlStateManager.color(0.1f, 0.7f, 0.1f, 1f);
				case WHITE_CHECK:
					CHECK_ICON.drawIcon(x, y);
					break;
				case PUZZLE_FAIL:
					GlStateManager.color(1, 0, 0, 1);
					X_ICON.drawIcon(x, y);
					break;
			}
		}
	}
}
