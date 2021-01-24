package pet.jerry.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import pet.jerry.Jerry;
import pet.jerry.feature.AbstractFeature;
import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.feature.Feature;
import pet.jerry.screen.configure.FeatureConfigureButton;

import java.util.*;
import java.util.stream.Collectors;

public class FeaturesList extends GuiListExtended {
	private final List<FeatureEntry> entries = new ArrayList<>();
	private List<FeatureEntry> displayedEntries;
	private final GuiScreen owner;
	private final GuiTextField searchField;

	public FeaturesList(GuiScreen owner) {
		super(Minecraft.getMinecraft(), owner.width, owner.height, 32, owner.height - 24, 24);
		this.owner = owner;
		List<Feature> featureList = new ArrayList<>(Jerry.INSTANCE.getFeatureRegistry().getItems());
		featureList.sort(Comparator.comparing(Feature::getName));
		for (Feature feature : featureList) {
			this.entries.add(new FeatureEntry(feature));
		}
		this.displayedEntries = entries;
		this.searchField = new GuiTextField(64, mc.fontRendererObj, 0, 6, 100, 20);
		this.searchField.setFocused(true);
	}

	public void keyTyped(char typedChar, int keyCode) {
		this.searchField.textboxKeyTyped(typedChar, keyCode);
		if(this.searchField.getText().trim().isEmpty()) {
			this.displayedEntries = entries;
		} else {
			this.displayedEntries = entries.stream().filter((entry) ->
					entry.feature.getName().toLowerCase(Locale.ROOT).contains(this.searchField.getText().toLowerCase(Locale.ROOT))).collect(Collectors.toList());
		}
	}

	@Override
	public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
		super.drawScreen(mouseXIn, mouseYIn, p_148128_3_);
		this.searchField.xPosition = this.getScrollBarX() - this.searchField.width - 2;
		this.searchField.drawTextBox();
	}

	@Override
	public int getListWidth() {
		return owner.width;
	}

	@Override
	protected int getScrollBarX() {
		return this.getListWidth() - 30;
	}

	@Override
	protected void drawContainerBackground(Tessellator tessellator) {
	}

	@Override
	public IGuiListEntry getListEntry(int index) {
		return this.displayedEntries.get(index);
	}

	@Override
	protected int getSize() {
		return this.displayedEntries.size();
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
		this.searchField.mouseClicked(mouseX, mouseY, mouseEvent);
		return super.mouseClicked(mouseX, mouseY, mouseEvent);
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
				if (((AbstractFeature) feature).getContainer().getValue().size() > min) {
					this.children.add(new FeatureConfigureButton(1, (AbstractFeature) feature, FeaturesList.this.owner));
				}
			}

		}

		@Override
		public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
			mc.fontRendererObj.drawStringWithShadow(feature.getName(), x + 30, y + 8, 0xffffff);
			int buttonX = FeaturesList.this.getScrollBarX();
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
