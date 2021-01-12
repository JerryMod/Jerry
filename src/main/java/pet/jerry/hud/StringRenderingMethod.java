package pet.jerry.hud;

import net.minecraft.client.gui.FontRenderer;

public enum StringRenderingMethod {
	SHADOW {
		@Override
		public void renderString(FontRenderer fontRenderer, String text, float x, float y, int colour) {
			fontRenderer.drawStringWithShadow(text, x, y, colour);
		}
	},
	OUTLINE {
		@Override
		public void renderString(FontRenderer fontRenderer, String text, float x, float y, int colour) {
			int outlineColour = (colour & 16579836) >> 2 | colour & -16777216;
			fontRenderer.drawString(text, x, y - 1, outlineColour, false);
			fontRenderer.drawString(text, x, y + 1, outlineColour, false);
			fontRenderer.drawString(text, x - 1, y, outlineColour, false);
			fontRenderer.drawString(text, x + 1, y, outlineColour, false);
			fontRenderer.drawString(text, x, y, colour, false);
		}
	};

	public abstract void renderString(FontRenderer fontRenderer, String text, float x, float y, int colour);
}
