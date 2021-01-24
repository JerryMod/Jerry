package pet.jerry.hud;

import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.hud.group.DefaultRenderGroup;
import pet.jerry.hud.group.RenderGroup;
import pet.jerry.hud.position.Position;
import pet.jerry.value.SimpleValue;
import pet.jerry.value.Value;

public abstract class AbstractHUDElement extends AbstractToggleableFeature implements HUDElement {
	private final Value<Position> position;
	private StringRenderingMethod stringRenderingMethod = StringRenderingMethod.OUTLINE;

	public AbstractHUDElement() {
		this(DefaultRenderGroup.get());
	}

	public AbstractHUDElement(RenderGroup group) {
		this(new Position(0, 0), group);
	}

	public AbstractHUDElement(Position position) {
		this(position, DefaultRenderGroup.get());
	}

	public AbstractHUDElement(Position position, RenderGroup group) {
		this.position = new SimpleValue<>("Position", "position", position);
		this.getContainer().add(this.position);
		group.addMember(this);
	}

	public AbstractHUDElement(String id, String name) {
		this(id, name, DefaultRenderGroup.get());
	}

	public AbstractHUDElement(String id, String name, RenderGroup group) {
		super(id, name);
		this.position = new SimpleValue<>("Position", "position", new Position(0, 0));
		this.getContainer().add(this.position);
		group.addMember(this);
	}

	@Override
	public Position getPosition() {
		return position.getValue();
	}

	protected void drawString(String text, float x, float y, int colour) {
		stringRenderingMethod.renderString(mc.fontRendererObj, text, x, y, colour);
	}

	protected StringRenderingMethod getStringRenderingMethod() {
		return stringRenderingMethod;
	}
}
