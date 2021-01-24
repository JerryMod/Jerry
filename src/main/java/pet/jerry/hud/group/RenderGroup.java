package pet.jerry.hud.group;

import pet.jerry.data.base.SkyBlock;
import pet.jerry.hud.HUDElement;

import java.util.Set;

public interface RenderGroup {
	boolean canRender(SkyBlock skyBlock);
	Set<HUDElement> getMembers();
	void addMember(HUDElement element);
}
