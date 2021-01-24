package pet.jerry.hud.group;

import pet.jerry.Jerry;
import pet.jerry.hud.HUDElement;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRenderGroup implements RenderGroup {
	private final Set<HUDElement> members = new HashSet<>();

	protected AbstractRenderGroup() {
		Jerry.INSTANCE.getRenderGroupRegistry().register(this);
	}

	@Override
	public Set<HUDElement> getMembers() {
		return members;
	}

	@Override
	public void addMember(HUDElement element) {
		this.members.add(element);
	}
}
