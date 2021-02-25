package pet.jerry.screen.configure.element;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.client.config.GuiSlider;
import pet.jerry.value.Value;
import pet.jerry.value.constraint.Constraint;
import pet.jerry.value.constraint.NumberClampConstraint;
import pet.jerry.value.number.DoubleValue;
import pet.jerry.value.number.FloatValue;
import pet.jerry.value.number.NumberValue;

public class NumberValueSlider extends GuiSlider {
	private static final Minecraft mc = Minecraft.getMinecraft();

	private final Value<? extends Number> value;

	public NumberValueSlider(int id, Value<? extends Number> value) {
		super(id, 0, 0, 100, 20, "", "", 0, 100, value.getValue().doubleValue(),
				value.getValue() instanceof Float || value.getValue() instanceof Double, true);
		this.value = value;
		if(this.showDecimal) {
			this.precision = 1;
		}

		double value1 = this.getValue();
		for (Constraint<?> constraint : value.getConstraints()) {
			if(constraint instanceof NumberClampConstraint) {
				NumberClampConstraint<?> constraint1 = (NumberClampConstraint<?>) constraint;

				this.minValue = constraint1.getLowerBound().doubleValue();
				this.maxValue = constraint1.getUpperBound().doubleValue();
			}
		}
		this.setValue(value1);

		this.updateSlider();

		if(value.getID().equalsIgnoreCase("border_width")) {
			System.out.println("Constructing slider.");
			System.out.printf("value value: %f, slider value: %f%n", value.getValue().doubleValue(), this.getValue());
		}
	}

	@Override
	public void updateSlider() {
		super.updateSlider();
		this.updateValue();
		this.setValue(this.value.getValue().doubleValue());
	}

	@Override
	public void mouseReleased(int par1, int par2) {
		this.dragging = false;
		if(par1 > this.xPosition && par1 < this.xPosition + this.width
				&& par2 > this.yPosition && par2 < this.yPosition + this.width) {
			this.playPressSound(mc.getSoundHandler());
		}
	}

	@Override
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
		if(super.mousePressed(par1Minecraft, par2, par3)) {
			this.playPressSound(mc.getSoundHandler());
			return true;
		}
		return false;
	}

	private void updateValue() {
		if(value.getValue() instanceof Integer) {
			((Value<Integer>) value).setValue(this.getValueInt());
		} else if(value.getValue() instanceof Float) {
			((Value<Float>) value).setValue((float) this.getValue());
		} else if(value.getValue() instanceof Double) {
			((Value<Double>) value).setValue(this.getValue());
		}
		this.setValue(value.getValue().doubleValue());
	}
}
