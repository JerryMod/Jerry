package pet.jerry.hud;

import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.value.SimpleValue;
import pet.jerry.value.Value;

public abstract class AbstractHUDElement extends AbstractToggleableFeature implements HUDElement {
	private final Value<Position> position;

	public AbstractHUDElement(Position position) {
		this.position = new SimpleValue<>("Position", "position", position);
		this.getContainer().add(this.position);
	}

	@Override
	public Position getPosition() {
		return position.getValue();
	}
}
