package pet.jerry.feature.features.dungeon.map.render.custom;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import pet.jerry.feature.features.dungeon.map.Room;

public class LRoomRenderer extends RoomRenderer {
	public LRoomRenderer() {
		super(Room.Shape.L_SHAPED);
	}

	@Override
	public void draw(Room room) {
		Pair<Integer, Integer> cornerTile = this.findCornerTile(room.getRepresentation());
		int cornerX = cornerTile.getLeft();
		int cornerY = cornerTile.getRight();
		int tileSize = room.getTileSize();

		int emptyX = (cornerX ^ 1) * (tileSize + 4);
		int emptyY = (cornerY ^ 1) * (tileSize + 4);

		int startX = (cornerX * (tileSize + 4));
		int startY = (cornerY * (tileSize + 4));

		Gui.drawRect(startX, 0, startX + tileSize, 2 * (tileSize) + 4, room.getColour());
		Gui.drawRect(Math.min(emptyX, startX), startY, 2 * (tileSize) + 4, startY + tileSize, room.getColour());

		this.drawExits(room);
	}

	@Override
	public Pair<Float, Float> getCentrePoint(Room room) {
		Pair<Integer, Integer> cornerTile = this.findCornerTile(room.getRepresentation());
		return new ImmutablePair<>(
				(cornerTile.getLeft() * (room.getPrincipalTile().getTileSize() + 4)) + room.getPrincipalTile().getTileSize() / 2f,
				(cornerTile.getRight() * (room.getPrincipalTile().getTileSize() + 4)) + room.getPrincipalTile().getTileSize() / 2f);
	}

	private Pair<Integer, Integer> findCornerTile(byte[][] representation) {
		for (byte x = 0; x < representation.length; x++) {
			for (byte y = 0; y < representation[0].length; y++) {
				if (representation[x][y] == 0)
					return new ImmutablePair<>(x ^ 1, y ^ 1);
			}
		}
		throw new RuntimeException("L room corner could not be ascertained. ???");
	}
}
