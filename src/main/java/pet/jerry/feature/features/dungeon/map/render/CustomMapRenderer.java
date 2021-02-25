package pet.jerry.feature.features.dungeon.map.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import pet.jerry.feature.features.dungeon.DungeonMapDisplayFeature;
import pet.jerry.feature.features.dungeon.map.MapGrid;
import pet.jerry.feature.features.dungeon.map.Room;
import pet.jerry.feature.features.dungeon.map.render.custom.*;
import pet.jerry.hud.icon.ImageIcon;
import pet.jerry.value.DefaultSaveableContainer;
import pet.jerry.value.NamedColour;
import pet.jerry.value.annotation.Clamp;
import pet.jerry.value.annotation.Value;
import pet.jerry.value.reflection.ReflectedValueFinder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CustomMapRenderer implements MapRenderer {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final int SIZE = 128;
	private static final ImageIcon LOCATION_POINTER = new ImageIcon(new ResourceLocation("jerry:map/pointer.png"), 32, 6);

	private final DefaultSaveableContainer container = new DefaultSaveableContainer("Custom Map Options", "custom_map");

	private final DungeonMapDisplayFeature parent;

	@Value(name = "Icon Style", id = "icon_renderer")
	private IconType iconType = IconType.IMAGE;

	@Value(name = "Centre Icons", id = "centre_icons")
	private boolean shouldCentreIcons = true;

	@Value(name = "Zoom", id = "zoom")
	@Clamp(min = 1, max = 2)
	private double zoomLevel = 1.3;

	private final NamedColour playerMarkerColour = new NamedColour("Player Marker Colour", "marker_colour");

	private final List<RoomRenderer> roomRenderers = new LinkedList<>(Arrays.asList(
			new GeometryRoomRenderer(),
			new LRoomRenderer()
	));

	public CustomMapRenderer(DungeonMapDisplayFeature parent) {
		this.parent = parent;
		new ReflectedValueFinder(this).find(this.container);
		this.playerMarkerColour.setColour(0xffdedede);
		this.container.add(playerMarkerColour);
	}

	@Override
	public void drawMap(MapData mapData) {
		if (mapData == null || mapData.colors == null)
			return;
		if (mapData.colors[0] / 4 == 0) {
			final MapGrid mapGrid = new MapGrid(mapData);

			// todo this doesn't calculate properly
			double mathedX = ((mc.thePlayer.posX * (mapGrid.getTileSize() / 30.0))); /* 30 is the size in the world of a 1x1 room */
			double mathedZ = ((mc.thePlayer.posZ * (mapGrid.getTileSize() / 30.0)));

			double clampedX = MathHelper.clamp_double(mathedX, 0, SIZE);
			double clampedZ = MathHelper.clamp_double(mathedZ, 0, SIZE);

			double relativeX = clampedX / SIZE;
			double relativeZ = clampedZ / SIZE;

			double scaledSize = SIZE * zoomLevel;

			double zoomedX = (relativeX * scaledSize);
			double zoomedZ = (relativeZ * scaledSize);

			zoomedX = MathHelper.clamp_double(zoomedX - (SIZE / 2.0), 0, SIZE);
			zoomedZ = MathHelper.clamp_double(zoomedZ - (SIZE / 2.0), 0, SIZE);

			GlStateManager.pushMatrix();
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.disableDepth();
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			if (this.parent.isRotateMap()) {
				GlStateManager.translate(64, 64, 0);
				GlStateManager.rotate(180 - mc.thePlayer.rotationYaw, 0, 0, 1);
				GlStateManager.translate(-63, -63, 0);
			}

			zoomedX = MathHelper.clamp_double((-zoomedX), -(scaledSize - SIZE), 0);
			zoomedZ = MathHelper.clamp_double((-zoomedZ), -(scaledSize - SIZE), 0);

			GlStateManager.translate(zoomedX, zoomedZ, 0);
			GlStateManager.scale(zoomLevel, zoomLevel, zoomLevel);
			if (mapGrid.getRoomIDs().length > 0) {
				for (Room room : mapGrid.getRooms().values()) {
					for (RoomRenderer roomRenderer : this.roomRenderers) {
						if (roomRenderer.getRenderableShapes().contains(room.getShape())) {
							GlStateManager.pushMatrix();
							this.iconType.getIconRenderer().setShouldCentre(this.shouldCentreIcons);
							GlStateManager.translate(room.getLowestRenderX(), room.getLowestRenderY(), 0);
							GlStateManager.enableBlend();
							roomRenderer.draw(room);
							Pair<Float, Float> iconPoint = this.iconType.getIconRenderer().shouldCentre()
									? roomRenderer.getCentrePoint(room) : new ImmutablePair<>(2f, 2f);
							float iconX = iconPoint.getLeft();
							float iconY = iconPoint.getRight();
							if (parent.isRotateMap()) {
								GlStateManager.translate(iconX, iconY, 0);
								GlStateManager.rotate(-(180 - mc.thePlayer.rotationYaw), 0, 0, 1);
								GlStateManager.translate(-iconX, -iconY, 0);
							}
							this.iconType.getIconRenderer().drawIcon(room, iconX, iconY);
							GlStateManager.translate(-room.getLowestRenderX(), -room.getLowestRenderY(), 0);
							GlStateManager.popMatrix();
							break;
						}
					}
				}
			}
			GlStateManager.translate(clampedX, clampedZ, 0);
			GlStateManager.rotate(-(180 - mc.thePlayer.rotationYaw), 0, 0, 1);
			float zz = LOCATION_POINTER.getDimension().getWidth() / 2;
			this.playerMarkerColour.applyGL();
			GlStateManager.scale(1 / zoomLevel, 1 / zoomLevel, 1 / zoomLevel);
			LOCATION_POINTER.drawIcon(-zz, -zz);
			GlStateManager.translate(-clampedX, -clampedZ, 0);
			GlStateManager.translate(-zoomedX, -zoomedZ, 0);
			GlStateManager.color(1f, 1f, 1f, 1f);
			GlStateManager.popMatrix();
		}

	}

	@Override
	public DefaultSaveableContainer getConfiguration() {
		return this.container;
	}

	@Override
	public DungeonMapDisplayFeature getParentFeature() {
		return parent;
	}

	enum IconType {
		TEXT(new TextIconRenderer()),
		IMAGE(new ImageIconRenderer());

		private final IconRenderer iconRenderer;

		IconType(IconRenderer iconRenderer) {
			this.iconRenderer = iconRenderer;
		}

		public IconRenderer getIconRenderer() {
			return iconRenderer;
		}
	}
}
