package pet.jerry.hud;

import pet.jerry.core.Named;
import pet.jerry.data.base.SkyBlock;

import java.util.List;

public interface HUDElement extends Named {
	void draw(float x, float y, SkyBlock skyBlock);
	Dimension getDimension(SkyBlock skyBlock);
	Position getPosition();
	boolean canRender(SkyBlock skyBlock);
}
