package pet.jerry.hud;

import pet.jerry.feature.AbstractToggleableFeature;

public abstract class AbstractHUDElement extends AbstractToggleableFeature implements HUDElement {
	private final Position position;

	public AbstractHUDElement(Position position) {
		this.position = position;
	}

	@Override
	public Position getPosition() {
		return position;
	}
}
