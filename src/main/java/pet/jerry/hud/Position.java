package pet.jerry.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Position {
	private float x;
	private float y;

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Position toAbsolute(Dimension dimension) {
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		return new Position(this.getX() * scaledResolution.getScaledWidth() - (this.getX() * dimension.getWidth()),
				this.getY() * scaledResolution.getScaledHeight() - (this.getY() * dimension.getHeight()));
	}
}
