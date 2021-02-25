package pet.jerry.feature.features.dungeon;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;
import pet.jerry.data.base.SkyBlock;
import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.feature.features.dungeon.map.render.CustomMapRenderer;
import pet.jerry.feature.features.dungeon.map.render.DefaultMapRenderer;
import pet.jerry.feature.features.dungeon.map.render.MapRenderer;
import pet.jerry.hud.AbstractHUDElement;
import pet.jerry.hud.icon.ImageIcon;
import pet.jerry.hud.position.Dimension;
import pet.jerry.value.NamedColour;
import pet.jerry.value.annotation.Clamp;
import pet.jerry.value.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@FeatureInfo(id = "dungeon_map", name = "Dungeon Map", category = FeatureCategory.DUNGEON)
public class DungeonMapDisplayFeature extends AbstractHUDElement {
	private static final int SIZE = 128;
	private static final Dimension MAP_DIMENSION = new Dimension(SIZE, SIZE);

	@Value(name = "Rotate Map", id = "rotate")
	private boolean rotateMap = true;

	@Value(name = "Border Width", id = "border_width")
	@Clamp(min = 1, max = 10)
	private float borderWidth = 6f;

	@Value(name = "Map Type", id = "map_type")
	private MapType mapType = MapType.CUSTOM;

	private final NamedColour borderColour = new NamedColour("Border Colour", "border_colour");

	private final Map<MapType, MapRenderer> mapRenderers = new HashMap<>();

	public DungeonMapDisplayFeature() {
		mapRenderers.put(MapType.CUSTOM, new CustomMapRenderer(this));
		mapRenderers.put(MapType.DEFAULT, new DefaultMapRenderer(this));
		for (MapType value : MapType.values()) {
			this.add(mapRenderers.get(value).getConfiguration());
		}
		this.add(borderColour);
		this.borderColour.setColour(0xff555555);
	}

	@Override
	public void draw(SkyBlock skyBlock) {
		if (this.isEnabled() && skyBlock.getCurrentDungeon() != null && mc.thePlayer != null && mc.theWorld != null) {
			ItemStack stack = mc.thePlayer.inventory.mainInventory[8];
			if (stack != null && stack.getItem() != null && stack.getItem().equals(Items.filled_map)) {
				Gui.drawRect(-SIZE, -SIZE, SIZE * 2, SIZE * 2, 0x33000000);

				MapData mapData = Items.filled_map.getMapData(stack, mc.theWorld);
				if (mapData == null)
					return;

				this.mapRenderers.get(mapType).drawMap(mapData);

				GlStateManager.pushMatrix();
				GlStateManager.disableTexture2D();
				GL11.glLineWidth(this.borderWidth);
				GL11.glBegin(GL11.GL_LINE_LOOP);
				this.borderColour.applyGL();
				GL11.glVertex2d(0, 0);
				GL11.glVertex2d(0, SIZE);
				GL11.glVertex2d(SIZE, SIZE);
				GL11.glVertex2d(SIZE, 0);
				GL11.glEnd();
				GlStateManager.enableTexture2D();
				GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public Dimension getDimension(SkyBlock skyBlock) {
		return MAP_DIMENSION;
	}

	public boolean isRotateMap() {
		return rotateMap;
	}

	enum MapType {
		CUSTOM,
		DEFAULT
	}
}
