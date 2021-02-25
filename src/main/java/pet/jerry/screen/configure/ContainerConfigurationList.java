package pet.jerry.screen.configure;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import pet.jerry.feature.AbstractFeature;
import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.feature.Feature;
import pet.jerry.screen.FeatureToggleSwitchElement;
import pet.jerry.screen.FeaturesList;
import pet.jerry.screen.configure.element.*;
import pet.jerry.value.*;

import java.util.ArrayList;
import java.util.List;

class ContainerConfigurationList extends GuiListExtended {
	private final List<IGuiListEntry> entryList = new ArrayList<>();
	private final SaveableContainer container;
	private final GuiScreen owner;

	public ContainerConfigurationList(GuiScreen owner, SaveableContainer container) {
		super(Minecraft.getMinecraft(), owner.width, owner.height, 30, owner.height - 24, 24);
		this.owner = owner;
		this.container = container;

		for (Saveable<?> saveable : container.getValue()) {
			IGuiListEntry entry = this.createFeatureEntry(saveable);
			if(null != entry) {
				this.entryList.add(entry);
			}
		}
	}

	private IGuiListEntry createFeatureEntry(Saveable<?> value) {
		Class<?> clazz = value.getValue().getClass();
		while (clazz.isAnonymousClass()) {
			clazz = clazz.getSuperclass();
		}

		if(value instanceof Feature) {
			return new FeatureEntry((Feature) value);
		} if(value instanceof NamedColour) {
			return new NamedColourEntry((NamedColour) value);
		} else if(value instanceof SaveableContainer) {
			SaveableContainer container = (SaveableContainer) value;
			if(container.getValue().isEmpty())
				return null;
			return new ContainerGuiEntry(container);
		} else if(clazz.equals(Boolean.class)
				|| clazz.equals(Boolean.TYPE)) {
			return new BooleanValueEntry((Value<Boolean>) value);
		} else if(clazz.equals(Float.class) || clazz.equals(Float.TYPE)
				|| clazz.equals(Integer.class) || clazz.equals(Integer.TYPE)
				|| clazz.equals(Double.class) || clazz.equals(Double.TYPE)) {
			return new NumberValueEntry((Value<Number>) value);
		} else if(clazz.isEnum()) {
			return new EnumValueEntry((Value<Enum<?>>) value);
		}

		return null;
	}

	@Override
	protected void drawSlot(int slotIndex, int x, int y, int slotHeight, int mouseXIn, int mouseYIn) {
		int yOffset = 2;
		if(slotIndex % 2 == 0)
			Gui.drawRect(x, y - yOffset, x + this.getListWidth(), y + slotHeight + yOffset, 0x22ffffff);
		super.drawSlot(slotIndex, x, y, slotHeight, mouseXIn, mouseYIn);
	}

	@Override
	protected int getSize() {
		return entryList.size();
	}

	@Override
	public IGuiListEntry getListEntry(int index) {
		return entryList.get(index);
	}

	@Override
	protected int getScrollBarX() {
		return owner.width - 30;
	}

	@Override
	public int getListWidth() {
		return owner.width;
	}

	@Override
	protected void drawContainerBackground(Tessellator tessellator) {
	}

	class ContainerGuiEntry implements IGuiListEntry {
		private final SaveableContainer container;
		private final GuiButton openScreenButton = new GuiButton(0, 0, 0, 20, 20, ">");

		ContainerGuiEntry(SaveableContainer container) {
			this.container = container;
		}

		@Override
		public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
			int yOffset = (slotHeight / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2);
			mc.fontRendererObj.drawStringWithShadow(container.getName(), x + 30, y + yOffset, 0xffffff);
			openScreenButton.xPosition = ContainerConfigurationList.this.getScrollBarX() - 4 - openScreenButton.width;
			openScreenButton.yPosition = y;
			openScreenButton.drawButton(mc, mouseX, mouseY);
		}

		@Override
		public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
			if(openScreenButton.mousePressed(mc, p_148278_2_, p_148278_3_)) {
				mc.displayGuiScreen(new ContainerConfigurationScreen(ContainerConfigurationList.this.owner, container));
				return true;
			}
			return false;
		}

		@Override
		public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
			this.openScreenButton.mouseReleased(relativeX, relativeY);
		}

		@Override
		public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) { }
	}

	abstract class ValueEntry<T> implements IGuiListEntry {
		protected final Value<T> value;
		protected final GuiButton controlElement;

		ValueEntry(Value<T> value) {
			this.value = value;
			this.controlElement = createControlElement();
		}

		protected abstract GuiButton createControlElement();

		protected Value<T> getValue() {
			return value;
		}

		@Override
		public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
			int yOffset = (slotHeight / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2);
			mc.fontRendererObj.drawStringWithShadow(value.getName(), x + 30, y + yOffset, 0xffffff);
			controlElement.xPosition = ContainerConfigurationList.this.getScrollBarX() - 4 - controlElement.width;
			controlElement.yPosition = y;
			controlElement.drawButton(mc, mouseX, mouseY);
		}

		@Override
		public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
			return controlElement.mousePressed(mc, p_148278_2_, p_148278_3_);
		}

		@Override
		public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
			controlElement.mouseReleased(x, y);
		}

		@Override
		public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) { }
	}

	class EnumValueEntry extends ValueEntry<Enum<?>> {
		EnumValueEntry(Value<Enum<?>> value) {
			super(value);
		}

		@Override
		protected GuiButton createControlElement() {
			return new EnumSelectButton(0, value);
		}
	}

	class NumberValueEntry extends ValueEntry<Number> {
		NumberValueEntry(Value<Number> value) {
			super(value);
		}

		@Override
		protected GuiButton createControlElement() {
			return new NumberValueSlider(0, this.getValue());
		}
	}

	class BooleanValueEntry extends ValueEntry<Boolean> {
		BooleanValueEntry(Value<Boolean> value) {
			super(value);
		}

		@Override
		protected GuiButton createControlElement() {
			return new ValueToggleSwitch(0, this.getValue());
		}
	}

	class NamedColourEntry implements IGuiListEntry {
		private final NamedColour colour;
		private final ColourEditButton colourEditButton;

		NamedColourEntry(NamedColour colour) {
			this.colour = colour;
			this.colourEditButton = new ColourEditButton(0, colour, ContainerConfigurationList.this.owner);
		}

		@Override
		public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
			int yOffset = (slotHeight / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2);
			mc.fontRendererObj.drawStringWithShadow(colour.getName(), x + 30, y + yOffset, 0xffffff);
			colourEditButton.xPosition = ContainerConfigurationList.this.getScrollBarX() - 4 - colourEditButton.width;
			colourEditButton.yPosition = y;
			colourEditButton.drawButton(mc, mouseX, mouseY);
		}

		@Override
		public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
			return colourEditButton.mousePressed(mc, p_148278_2_, p_148278_3_);
		}

		@Override
		public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
			colourEditButton.mouseReleased(x, y);
		}

		@Override
		public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) { }
	}

	class FeatureEntry implements IGuiListEntry {
		private final Feature feature;
		private final List<GuiButton> children = new ArrayList<>();

		FeatureEntry(Feature feature) {
			this.feature = feature;
			if (feature instanceof AbstractFeature) {
				int min = 0;
				if (feature instanceof AbstractToggleableFeature) {
					this.children.add(new FeatureToggleSwitchElement(0, (AbstractToggleableFeature) feature));
					min = 1;
				}
				if (feature.getValue().size() > min) {
					this.children.add(new FeatureConfigureButton(1, (AbstractFeature) feature, ContainerConfigurationList.this.owner));
				}
			}

		}

		@Override
		public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
			int yOffset = (slotHeight / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2);
			mc.fontRendererObj.drawStringWithShadow(feature.getName(), x + 30, y + yOffset, 0xffffff);
			int buttonX = ContainerConfigurationList.this.getScrollBarX();
			for (GuiButton button : children) {
				button.xPosition = (buttonX -= (button.width + 4));
				button.yPosition = y;
				button.drawButton(mc, mouseX, mouseY);
			}
		}

		@Override
		public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
			for (GuiButton button : children) {
				if (button.mousePressed(mc, mouseX, mouseY)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
		}

		@Override
		public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
		}
	}
}
