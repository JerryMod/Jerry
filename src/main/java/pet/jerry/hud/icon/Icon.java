package pet.jerry.hud.icon;

import pet.jerry.hud.position.Dimension;

public interface Icon {
	void drawIcon(float x, float y);
	Dimension augmentDimension(Dimension dimension);
	Dimension getDimension();
	IconLocation getIconLocation();
}
