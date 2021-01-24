package pet.jerry.hud.group;

import pet.jerry.core.Registry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class RenderGroupRegistry implements Registry<RenderGroup> {
	private final Set<RenderGroup> groups;

	public RenderGroupRegistry() {
		groups = new HashSet<>();
	}

	@Override
	public Collection<RenderGroup> getItems() {
		return groups;
	}

	@Override
	public void register(RenderGroup item) {
		this.groups.add(item);
	}
}
