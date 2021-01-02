package pet.jerry.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import pet.jerry.feature.AbstractToggleableFeature;

public abstract class ToggleSwitchElement extends GuiButton {
	public ToggleSwitchElement(int id) {
		super(id, 0, 0, "");
		this.width = 40;
	}

	protected abstract boolean isEnabled();
	protected abstract void toggle();

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(buttonTextures);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		float pos = (this.isEnabled() ? this.width - 8 : 0);
		String label = this.isEnabled() ? "ON" : "OFF";
		this.enabled = false;
		super.drawButton(mc, mouseX, mouseY);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.enabled = true;
		int i = this.hovered ? 20 : 0;
		this.drawTexturedModalRect(this.xPosition + pos, this.yPosition, 0, 66 + i, 4, 20);
		this.drawTexturedModalRect(this.xPosition + pos + 4, this.yPosition, 196, 66 + i, 4, 20);
		int offset = this.isEnabled() ? -1 : 2;
		this.drawString(mc.fontRendererObj, label,
				(this.xPosition + ((this.width / 2) - (mc.fontRendererObj.getStringWidth(label) / 2))) + offset, this.yPosition + 6, 0xFFFFFF);
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if(mouseX >= this.xPosition
				&& mouseY >= this.yPosition
				&& mouseX < this.xPosition + this.width
				&& mouseY < this.yPosition + this.height) {
			this.playPressSound(mc.getSoundHandler());
			this.toggle();
			return true;
		}
		return false;
	}
}
