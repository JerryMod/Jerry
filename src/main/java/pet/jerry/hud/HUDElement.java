package pet.jerry.hud;

import pet.jerry.core.Named;

import java.util.List;

public interface HUDElement extends Named {
	void draw(float x, float y);
	Dimension getDimension();
	Position getPosition();
}
