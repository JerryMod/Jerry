package pet.jerry.feature.features.bar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import pet.jerry.data.base.SkyBlock;
import pet.jerry.feature.category.FeatureCategory;
import pet.jerry.hud.AbstractHUDElement;
import pet.jerry.hud.group.DefaultRenderGroup;
import pet.jerry.hud.position.Dimension;
import pet.jerry.value.NamedColour;

import java.util.Locale;
import java.util.function.Function;

public abstract class BarFeature extends AbstractHUDElement {
	private static final int WIDTH = 85;
	private static final int HEIGHT = 5;
	private static final Dimension DIMENSION = new Dimension(WIDTH, HEIGHT);

	private final NamedColour barColour = new NamedColour("Bar Colour", "bar_colour");
	private final Function<SkyBlock, Integer> currentValueSupplier;
	private final Function<SkyBlock, Integer> maxValueSupplier;

	BarFeature(String name,
	           Function<SkyBlock, Integer> currentValueSupplier,
	           Function<SkyBlock, Integer> maxValueSupplier) {
		super(name.toLowerCase(Locale.ROOT) + "_bar_display", name + " Bar Display", DefaultRenderGroup.get(), FeatureCategory.INFORMATION);
		this.add(barColour);
		this.currentValueSupplier = currentValueSupplier;
		this.maxValueSupplier = maxValueSupplier;
	}

	protected void setBarColour(int hex) {
		this.barColour.setColour(hex);
	}

	@Override
	public void draw(SkyBlock skyBlock) {
		int currentValue = currentValueSupplier.apply(skyBlock);
		int maxValue = maxValueSupplier.apply(skyBlock);
		float ratio = MathHelper.clamp_float((currentValue / (float) maxValue), 0, 1);
		float relativeX = this.getPosition().getX();

		GlStateManager.pushMatrix();
		GlStateManager.enableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		mc.getTextureManager().bindTexture(Gui.icons);
		this.barColour.applyGL();
		int texYCoord = 74;
		mc.ingameGUI.drawTexturedModalRect(0, 0, 0, texYCoord, WIDTH - 3, HEIGHT);
		mc.ingameGUI.drawTexturedModalRect((WIDTH - 3), 0, 179, texYCoord, 3, HEIGHT);
		int totalWidth = Math.round(WIDTH * ratio);
		int leftoverWidth = MathHelper.clamp_int(totalWidth - (WIDTH - 3), 0, 3);
		mc.ingameGUI.drawTexturedModalRect(0, 0, 0, texYCoord + 5, MathHelper.clamp_int(totalWidth, 0, WIDTH - 3), HEIGHT);
		if(leftoverWidth > 0) {
			mc.ingameGUI.drawTexturedModalRect((WIDTH - 3), 0, 179, texYCoord + 5, leftoverWidth, HEIGHT);
		}
		GlStateManager.popMatrix();
	}

	@Override
	public Dimension getDimension(SkyBlock skyBlock) {
		return DIMENSION;
	}
}
