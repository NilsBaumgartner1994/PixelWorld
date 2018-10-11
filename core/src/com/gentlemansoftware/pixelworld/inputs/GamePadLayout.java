package com.gentlemansoftware.pixelworld.inputs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gentlemansoftware.pixelworld.menuComponents.ControllerButtonOverlay;

public class GamePadLayout {

	int[] buttons = new int[GamePadButtons.values().length];
	ControllerButtonOverlay[] overlays = new ControllerButtonOverlay[GamePadButtons.values().length];

	public GamePadLayout() {

	}

	public void setEntry(GamePadButtons gamepadbutton, int keyCode, ControllerButtonOverlay overlay) {
		this.buttons[gamepadbutton.ordinal()] = keyCode;
		this.overlays[gamepadbutton.ordinal()] = overlay;
	}

	public void setEntry(GamePadButtons gamepadbutton, int keyCode, TextureRegion glyph, Color topBackground) {
		setEntry(gamepadbutton, keyCode, new ControllerButtonOverlay(glyph, topBackground));
	}

	public void setEntry(GamePadButtons gamepadbutton, int keyCode, TextureRegion glyph, Color topBackground,
			Color glyphColor) {
		setEntry(gamepadbutton, keyCode, new ControllerButtonOverlay(glyph, topBackground, glyphColor));
	}

	public GamePadButtons getButton(int buttonCode) {
		for (int i = 0; i < GamePadButtons.values().length; i++) {
			if (buttons[i] == buttonCode) {
				return GamePadButtons.values()[i];
			}
		}

		return GamePadButtons.UNKOWN;
	}

	public ControllerButtonOverlay getButton(GamePadButtons gamepadbutton) {
		return this.overlays[gamepadbutton.ordinal()];
	}

}
