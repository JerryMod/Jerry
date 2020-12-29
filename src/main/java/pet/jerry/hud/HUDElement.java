package pet.jerry.hud;

import pet.jerry.core.Named;

public interface HUDElement extends Named {
	void draw(float x, float y);
	Dimension getDimension();
	Position getPosition();
}
