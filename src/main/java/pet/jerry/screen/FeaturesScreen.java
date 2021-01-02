package pet.jerry.screen;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import pet.jerry.Jerry;
import pet.jerry.hud.HUDEditScreen;

import java.io.IOException;

public class FeaturesScreen extends GuiScreen {
	private FeaturesList featuresList;
	private GuiScreen parent;

	public FeaturesScreen() {
		this(null);
	}

	public FeaturesScreen(GuiScreen screen) {
		this.parent = screen;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.featuresList = new FeaturesList(this);
		this.buttonList.add(new GuiButton(0, (this.width / 2) - 154, this.height - 22, 150, 20, "Edit HUD..."));
		this.buttonList.add(new GuiButton(1, (this.width / 2) + 4, this.height - 22, 150, 20,"Done"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.featuresList.drawScreen(mouseX, mouseY, partialTicks);
		mc.fontRendererObj.drawStringWithShadow(Jerry.MOD_NAME + " Configuration", 30, 12, 0xffffff);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.featuresList.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.featuresList.handleMouseInput();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			Jerry.INSTANCE.getConfigRegistry().save();
			mc.displayGuiScreen(parent);
			if(parent == null) {
				mc.setIngameFocus();
			}
		} else {
			this.featuresList.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case 0:
				mc.displayGuiScreen(new HUDEditScreen(this));
				break;
			case 1:
				Jerry.INSTANCE.getConfigRegistry().save();
				mc.displayGuiScreen(parent);
				break;
			default:
		}
	}
}
