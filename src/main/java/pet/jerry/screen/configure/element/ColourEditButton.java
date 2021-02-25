package pet.jerry.screen.configure.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import pet.jerry.screen.configure.ColourEditScreen;
import pet.jerry.value.NamedColour;

public class ColourEditButton extends GuiButton {
	private final NamedColour colour;
	private final GuiScreen parent;

	public ColourEditButton(int buttonId, NamedColour colour, GuiScreen parent) {
		super(buttonId, 0, 0, 20, 20, "");
		this.colour = colour;
		this.parent = parent;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		super.drawButton(mc, mouseX, mouseY);
		this.drawRectWithOffset(3, 0xFFFFFFFF);
		this.drawRectWithOffset(4, colour.toHex(255));
	}

	private void drawRectWithOffset(int offset, int colour) {
		drawRect(xPosition + offset,
				yPosition + offset,
				xPosition + (width - offset),
				yPosition + (height - offset), colour);
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if(super.mousePressed(mc, mouseX, mouseY)) {
			this.playPressSound(mc.getSoundHandler());
			mc.displayGuiScreen(new ColourEditScreen(colour, parent));
			return true;
		}
		return false;
	}
}
