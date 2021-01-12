package pet.jerry.feature.features.bar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import pet.jerry.Jerry;
import pet.jerry.data.base.SkyBlock;
import pet.jerry.hud.AbstractHUDElement;
import pet.jerry.hud.Dimension;
import pet.jerry.hud.NamedColour;
import pet.jerry.value.BooleanValue;

import java.util.Locale;
import java.util.function.Supplier;

public abstract class BarFeature extends AbstractHUDElement {
	private static final int WIDTH = 85;
	private static final int HEIGHT = 5;
	private static final Dimension DIMENSION = new Dimension(WIDTH, HEIGHT);

	private final NamedColour barColour = new NamedColour("Bar Colour", "bar_colour");
	private final Supplier<Integer> currentValueSupplier;
	private final Supplier<Integer> maxValueSupplier;

	BarFeature(String name,
	           Supplier<Integer> currentValueSupplier,
	           Supplier<Integer> maxValueSupplier) {
		super(name.toLowerCase(Locale.ROOT) + "_bar_display", name + " Bar Display");
		this.getContainer().add(barColour);
		this.currentValueSupplier = currentValueSupplier;
		this.maxValueSupplier = maxValueSupplier;
	}

	protected void setBarColour(int hex) {
		this.barColour.setColour(hex);
	}

	@Override
	public void draw(float x, float y, SkyBlock skyBlock) {
		int currentValue = currentValueSupplier.get();
		int maxValue = maxValueSupplier.get();
		float ratio = MathHelper.clamp_float((currentValue / (float) maxValue), 0, 1);
		float relativeX = this.getPosition().getX();

		GlStateManager.pushMatrix();
		GlStateManager.enableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		mc.getTextureManager().bindTexture(Gui.icons);
		this.barColour.applyGL();
		int texYCoord = 74;
		mc.ingameGUI.drawTexturedModalRect(x, y, 0, texYCoord, WIDTH - 3, HEIGHT);
		mc.ingameGUI.drawTexturedModalRect(x + (WIDTH - 3), y, 179, texYCoord, 3, HEIGHT);
		int totalWidth = Math.round(WIDTH * ratio);
		int leftoverWidth = MathHelper.clamp_int(totalWidth - (WIDTH - 3), 0, 3);
		mc.ingameGUI.drawTexturedModalRect(x, y, 0, texYCoord + 5, MathHelper.clamp_int(totalWidth, 0, WIDTH - 3), HEIGHT);
		if(leftoverWidth > 0) {
			mc.ingameGUI.drawTexturedModalRect(x + (WIDTH - 3), y, 179, texYCoord + 5, leftoverWidth, HEIGHT);
		}
		GlStateManager.popMatrix();
	}

	@Override
	public Dimension getDimension(SkyBlock skyBlock) {
		return DIMENSION;
	}
}
