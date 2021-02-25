package pet.jerry.feature.features.dungeon.map.render.custom;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import pet.jerry.feature.features.dungeon.map.ConnectionType;
import pet.jerry.feature.features.dungeon.map.GridTile;
import pet.jerry.feature.features.dungeon.map.Room;
import pet.jerry.feature.features.dungeon.map.TileExit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public class GeometryRoomRenderer extends RoomRenderer {
	public GeometryRoomRenderer() {
		super(new HashSet<>(Arrays.asList(
				Room.Shape.ONE_BY_ONE,
				Room.Shape.ONE_BY_TWO,
				Room.Shape.ONE_BY_THREE,
				Room.Shape.ONE_BY_FOUR,
				Room.Shape.TWO_BY_TWO)));
	}

	@Override
	public Pair<Float, Float> getCentrePoint(Room room) {
		GridTile principalTile = room.getPrincipalTile();
		int xDimension = room.getRepresentation().length;
		int yDimension = room.getRepresentation()[0].length;
		int w = ((principalTile.getTileSize()) * xDimension) + ((xDimension - 1) * 4);
		int h = ((principalTile.getTileSize()) * yDimension) + ((yDimension - 1) * 4);
		return new ImmutablePair<>(w / 2f, h / 2f);
	}

	@Override
	public void draw(Room room) {
		int xDimension = room.getRepresentation().length;
		int yDimension = room.getRepresentation()[0].length;
		GridTile principalTile = room.getPrincipalTile();

		Gui.drawRect(0, 0,
				((principalTile.getTileSize()) * xDimension) + ((xDimension - 1) * 4),
				((principalTile.getTileSize()) * yDimension) + ((yDimension - 1) * 4),
				principalTile.getColour());

		this.drawExits(room);
	}
}
