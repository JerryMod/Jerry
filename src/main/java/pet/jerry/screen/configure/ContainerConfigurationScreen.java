package pet.jerry.screen.configure;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import pet.jerry.Jerry;
import pet.jerry.value.SaveableContainer;

import java.io.IOException;

public class ContainerConfigurationScreen extends GuiScreen {
	private final SaveableContainer container;
	private final GuiScreen parent;
	private ContainerConfigurationList configurationList;

	public ContainerConfigurationScreen() {
		this(null, Jerry.INSTANCE.getConfigRegistry().getRoot());
	}

	public ContainerConfigurationScreen(GuiScreen parent, SaveableContainer container) {
		this.parent = parent;
		this.container = container;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.configurationList = new ContainerConfigurationList(this, container);
		this.buttonList.add(new GuiButton(66, (this.width / 2) - 100, this.height - 22, "Back"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.configurationList.drawScreen(mouseX, mouseY, partialTicks);
		this.drawCenteredString(mc.fontRendererObj, container.getName(), this.width / 2, 10, 0xffffff);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.configurationList.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		this.configurationList.mouseReleased(mouseX, mouseY, state);
		super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	public void handleMouseInput() throws IOException {
		this.configurationList.handleMouseInput();
		super.handleMouseInput();
	}

	private void exitScreen() {
		mc.displayGuiScreen(parent);
		if (parent == null) {
			mc.setIngameFocus();
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			exitScreen();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 66) {
			exitScreen();
		}
	}
}
