package pet.jerry.util;

import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GLColour {
	private final float red;
	private final float green;
	private final float blue;
	private final float alpha;

	public GLColour(Color color) {
		this(color.getRed() / 255f,
				color.getGreen() / 255f,
				color.getBlue() / 255f,
				color.getAlpha() / 255f);
	}

	public GLColour(float red, float green, float blue) {
		this(red, green, blue, 1.0f);
	}

	public GLColour(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public void apply() {
		GL11.glColor4f(red, green, blue, alpha);
	}
}
