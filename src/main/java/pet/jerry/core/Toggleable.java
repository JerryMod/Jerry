package pet.jerry.core;

public interface Toggleable {
	boolean isEnabled();
	void setEnabled(boolean state);
	void toggle();
}
