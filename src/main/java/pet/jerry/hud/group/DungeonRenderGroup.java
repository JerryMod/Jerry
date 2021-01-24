package pet.jerry.hud.group;

import pet.jerry.data.base.SkyBlock;

public final class DungeonRenderGroup extends AbstractRenderGroup {
	private static final DungeonRenderGroup INSTANCE = new DungeonRenderGroup();

	private DungeonRenderGroup() {
		super();
	}

	@Override
	public boolean canRender(SkyBlock skyBlock) {
		return skyBlock.getCurrentDungeon() != null;
	}

	public static DungeonRenderGroup get() {
		return INSTANCE;
	}
}
