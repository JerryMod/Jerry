package pet.jerry.screen.configure;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import pet.jerry.feature.AbstractFeature;
import pet.jerry.value.NamedColour;
import pet.jerry.value.BooleanValue;
import pet.jerry.value.Saveable;
import pet.jerry.value.Value;

import java.util.ArrayList;
import java.util.List;

class FeatureConfigurationList extends GuiListExtended {
	private final List<IGuiListEntry> entryList = new ArrayList<>();
	private final AbstractFeature feature;
	private final GuiScreen owner;

	public FeatureConfigurationList(GuiScreen owner, AbstractFeature feature) {
		super(Minecraft.getMinecraft(), owner.width, owner.height, 30, owner.height - 24, 24);
		this.owner = owner;
		this.feature = feature;

		for (Saveable<?> saveable : feature.getContainer().getValue()) {
			IGuiListEntry entry = this.createFeatureEntry(saveable);
			if(null != entry) {
				this.entryList.add(entry);
			}
		}
	}

	private IGuiListEntry createFeatureEntry(Saveable<?> value) {
		if(value instanceof BooleanValue) {
			return new BooleanValueEntry((BooleanValue) value);
		} else if(value instanceof NamedColour) {
			return new NamedColourEntry((NamedColour) value);
		}

		return null;
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

	abstract class ValueEntry<T> implements IGuiListEntry {
		private final Value<T> value;
		private final GuiButton controlElement;

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
			mc.fontRendererObj.drawStringWithShadow(value.getName(), x + 30, y + 4, 0xFFFFFF);
			controlElement.xPosition = FeatureConfigurationList.this.getScrollBarX() - 4 - controlElement.width;
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
			this.colourEditButton = new ColourEditButton(0, colour, FeatureConfigurationList.this.owner);
		}

		@Override
		public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
			mc.fontRendererObj.drawStringWithShadow(colour.getName(), x + 30, y + 4, 0xFFFFFF);
			colourEditButton.xPosition = FeatureConfigurationList.this.getScrollBarX() - 4 - colourEditButton.width;
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
}
