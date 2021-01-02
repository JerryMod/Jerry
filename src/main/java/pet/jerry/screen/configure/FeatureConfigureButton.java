package pet.jerry.screen.configure;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import pet.jerry.feature.AbstractFeature;

public class FeatureConfigureButton extends GuiButton {
	private static final ResourceLocation GEAR_IMG = new ResourceLocation("jerry:gui/gear.png");
	private final AbstractFeature feature;
	private final GuiScreen parent;

	public FeatureConfigureButton(int buttonId, AbstractFeature feature, GuiScreen parent) {
		super(buttonId, 0, 0, 20, 20, "");
		this.feature = feature;
		this.parent = parent;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		super.drawButton(mc, mouseX, mouseY);
		GlStateManager.pushMatrix();
		GlStateManager.enableTexture2D();
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(GEAR_IMG);
		Gui.drawModalRectWithCustomSizedTexture(this.xPosition + 4, this.yPosition + 4, 0, 0,
				12, 12, 12, 12);
		GlStateManager.popMatrix();
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if(super.mousePressed(mc, mouseX, mouseY)) {
			this.playPressSound(mc.getSoundHandler());
			mc.displayGuiScreen(new FeatureConfigurationScreen(parent, feature));
			return true;
		}
		return false;
	}
}
