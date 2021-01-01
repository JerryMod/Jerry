package pet.jerry.feature.features.bar;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import pet.jerry.Jerry;
import pet.jerry.hud.AbstractHUDElement;
import pet.jerry.hud.Dimension;
import pet.jerry.hud.NamedColour;
import pet.jerry.value.BooleanValue;

import java.util.Locale;
import java.util.function.Supplier;

public abstract class BarFeature extends AbstractHUDElement {
	private static final int WIDTH = 85;
	private static final int HEIGHT = 7;
	private static final Dimension DIMENSION = new Dimension(WIDTH, HEIGHT);

	private final NamedColour barColour = new NamedColour("Bar Colour", "bar_colour");
	private final BooleanValue shrinkLeft = new BooleanValue("Always shrink left", "shrink_left");
	private final Supplier<Integer> currentValueSupplier;
	private final Supplier<Integer> maxValueSupplier;

	BarFeature(String name,
	           Supplier<Integer> currentValueSupplier,
	           Supplier<Integer> maxValueSupplier) {
		super(name.toLowerCase(Locale.ROOT) + "_bar_display", name + " Bar Display");
		this.getContainer().add(barColour, shrinkLeft);
		this.currentValueSupplier = currentValueSupplier;
		this.maxValueSupplier = maxValueSupplier;
	}

	protected void setBarColour(int hex) {
		this.barColour.setColour(hex);
	}

	@Override
	public void draw(float x, float y) {
		int currentValue = currentValueSupplier.get();
		int maxValue = maxValueSupplier.get();
		float ratio = MathHelper.clamp_float((currentValue / (float) maxValue), 0, 1);
		float relativeX = this.getPosition().getX();

		Gui.drawRect(Math.round(x), Math.round(y),
				Math.round(x + WIDTH), Math.round(y + HEIGHT), 0xFF333333);
		if(relativeX < 0.5 || shrinkLeft.getValue()) {
			Gui.drawRect(Math.round(x), Math.round(y),
					Math.round(x + (WIDTH * ratio)), Math.round(y + HEIGHT), barColour.toHex());
		} else {
			Gui.drawRect(Math.round(x + WIDTH - (WIDTH * ratio)), Math.round(y),
					Math.round(x + WIDTH), Math.round(y + HEIGHT), barColour.toHex());
		}
	}

	@Override
	public Dimension getDimension() {
		return DIMENSION;
	}
}
