package pet.jerry.hud;

import pet.jerry.data.base.SkyBlock;
import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.value.SimpleValue;
import pet.jerry.value.Value;

public abstract class AbstractHUDElement extends AbstractToggleableFeature implements HUDElement {
	private final Value<Position> position;

	public AbstractHUDElement() {
		this(new Position(0, 0));
	}

	public AbstractHUDElement(Position position) {
		this.position = new SimpleValue<>("Position", "position", position);
		this.getContainer().add(this.position);
	}

	public AbstractHUDElement(String id, String name) {
		super(id, name);
		this.position = new SimpleValue<>("Position", "position", new Position(0, 0));
		this.getContainer().add(this.position);
	}

	@Override
	public Position getPosition() {
		return position.getValue();
	}

	protected void drawString(String text, float x, float y, int colour) {
		StringRenderingMethod.OUTLINE.renderString(mc.fontRendererObj, text, x, y, colour);
	}

	@Override
	public boolean canRender(SkyBlock skyBlock) {
		return true;
	}
}
