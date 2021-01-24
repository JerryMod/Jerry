package pet.jerry.hud.group;

import pet.jerry.Jerry;
import pet.jerry.data.base.SkyBlock;

public final class DefaultRenderGroup extends AbstractRenderGroup {
	private static final DefaultRenderGroup INSTANCE = new DefaultRenderGroup();

	private DefaultRenderGroup() {
		super();
	}

	@Override
	public boolean canRender(SkyBlock skyBlock) {
		return true;
	}

	public static DefaultRenderGroup get() {
		return INSTANCE;
	}
}