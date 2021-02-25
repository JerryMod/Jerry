package pet.jerry.hud;

import pet.jerry.core.Named;
import pet.jerry.data.base.SkyBlock;
import pet.jerry.hud.group.RenderGroup;
import pet.jerry.hud.position.Dimension;
import pet.jerry.hud.position.Position;

public interface HUDElement extends Named {
	void draw(SkyBlock skyBlock);
	Dimension getDimension(SkyBlock skyBlock);
	Position getPosition();
}
