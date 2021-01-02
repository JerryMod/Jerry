package pet.jerry.screen.configure;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.lwjgl.input.Keyboard;
import pet.jerry.hud.NamedColour;

import java.awt.*;
import java.io.IOException;

public class ColourEditScreen extends GuiScreen {
	private final NamedColour colour;
	private final GuiScreen parent;

	private Color colorObj;
	private GuiSlider hue, saturation, brightness, alpha;
	private ColourTextField colourTextField;

	public ColourEditScreen(NamedColour colour, GuiScreen parent) {
		this.colour = colour;
		this.colorObj = new Color(colour.toHex());
		this.parent = parent;
	}

	@Override
	public void initGui() {
		int x = Math.round(this.width * (1f/6));
		int y = 40;
		int w = 100;
		int h = 20;

		float[] hsb = new float[3];
		Color.RGBtoHSB(colorObj.getRed(), colorObj.getGreen(), colorObj.getBlue(), hsb);
		this.buttonList.add(hue = new GuiSlider(0,
				x, y, w, h, "Hue: ", "%", 0, 100, hsb[0] * 100, false, true));
		this.buttonList.add(saturation = new GuiSlider(1,
				x, y += 25, w, h, "Saturation: ", "%", 0, 100, hsb[1] * 100, false, true));
		this.buttonList.add(brightness = new GuiSlider(2,
				x, y += 25, w, h, "Brightness: ", "%", 0, 100, hsb[2] * 100, false, true));
		this.buttonList.add(alpha = new GuiSlider(3,
				x, y += 25, w, h, "Alpha: ", "%", 0, 100, (colorObj.getAlpha() / 255f) * 100, false, true));
		this.buttonList.add(new GuiButton(66, (this.width / 2) - 100, this.height - 22, "Back"));
		this.colourTextField = new ColourTextField(4, mc.fontRendererObj, x, y += 25, w, h);
		this.colourTextField.setText(Integer.toHexString(this.colorObj.getRGB()));

		if(!colour.isAlphaAllowed()) {
			alpha.setValue(100);
			alpha.enabled = false;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawColourPreview();
		this.colourTextField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		this.updateColourPreview();
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.colourTextField.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}


	private void drawColourPreview() {
		int size = 50;
		int x = Math.round(this.width * (2f/3));
		int y = (this.height / 2) - (size / 2);
		GlStateManager.enableAlpha();
		Gui.drawRect(x, y, x + size, y + size, this.colorObj.getRGB());
		GlStateManager.disableAlpha();
		this.drawCenteredString(mc.fontRendererObj, "Preview", x + (size / 2), y - mc.fontRendererObj.FONT_HEIGHT - 2, 0xFFFFFF);
	}

	private void updateColourPreview() {
		Color color = Color.getHSBColor((float) hue.getValue() / 100f, (float) saturation.getValue() / 100f,
				(float) brightness.getValue() / 100f);

		this.colorObj = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round((this.alpha.getValueInt() / 100f) * 255));
		this.colourTextField.setText(Integer.toHexString(this.colorObj.getRGB()));
	}

	private void updateSliderPositions() {
		float[] hsb = new float[3];
		Color.RGBtoHSB(colorObj.getRed(), colorObj.getGreen(), colorObj.getBlue(), hsb);
		hue.setValue(hsb[0] * 100);
		saturation.setValue(hsb[1] * 100);
		brightness.setValue(hsb[2] * 100);

		if(this.colour.isAlphaAllowed())
			alpha.setValue((this.colorObj.getAlpha() / 255f) * 100);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			this.updateColourSetting();
			mc.displayGuiScreen(parent);
		} else {
			this.colourTextField.textboxKeyTyped(typedChar, keyCode);
			super.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 66) {
			this.updateColourSetting();
			mc.displayGuiScreen(parent);
		}
	}

	private void updateColourSetting() {
		this.colour.setColour(this.colorObj.getRGB());
	}

	class ColourTextField extends GuiTextField {
		private static final String HEX_CODE_REGEX = "#?([\\da-fA-F]{2})?([\\da-fA-F]{2})([\\da-fA-F]{2})([\\da-fA-F]{2})";

		public ColourTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
			super(componentId, fontrendererObj, x, y, par5Width, par6Height);
		}

		@Override
		public boolean textboxKeyTyped(char typedChar, int keyCode) {
			if(super.textboxKeyTyped(typedChar, keyCode)) {
				if(this.getText().matches(HEX_CODE_REGEX)) {
					try {
						ColourEditScreen.this.colorObj = new Color((int) Long.parseLong(this.getText(), 16));
						ColourEditScreen.this.updateSliderPositions();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("key code " + keyCode + " match: " + this.getText().matches(HEX_CODE_REGEX));
				}
				return true;
			}
			return false;
		}
	}
}
