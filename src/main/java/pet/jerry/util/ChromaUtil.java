package pet.jerry.util;

import java.awt.*;

public final class ChromaUtil {
	private ChromaUtil() {
	}

	public static int getChromaColour() {
		double time = System.currentTimeMillis() / 1000.0;
		//double sign = (Math.floor(Math.sin(time)) * 2 + 1);
		//float hue = (float) ((time % 100.0) / 100.0);
		//float hue = (float) Math.sin(sign * time);
		//System.out.printf("%f%n", hue);
		float hue = (float) (time - ((0.5 * Math.tanh(((time + 0.5) - Math.floor(time + 0.5)) * 50) + Math.floor(time + 0.5) - 0.5)));
		return Color.HSBtoRGB(hue, 1f, 1f);
	}
}
