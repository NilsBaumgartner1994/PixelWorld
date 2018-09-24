package com.gentlemansoftware.pixelworld.inputs;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gentlemansoftware.pixelworld.menuComponents.GlyphAndSymbols;

//This code was taken from http://www.java-gaming.org/index.php?topic=29223.0
//With thanks that is!

public class GamePadLayoutKeyboard extends GamePadLayout {

	public GamePadLayoutKeyboard() {
		this.buttons[GamePadButtons.BACK.ordinal()] = Keys.BACK;
		this.buttons[GamePadButtons.START.ordinal()] = Keys.ENTER;
		this.buttons[GamePadButtons.RIGHTPAD_DOWN.ordinal()] = Keys.K;
		this.buttons[GamePadButtons.RIGHTPAD_RIGHT.ordinal()] = Keys.L;
		this.buttons[GamePadButtons.RIGHTPAD_LEFT.ordinal()] = Keys.J;
		this.buttons[GamePadButtons.RIGHTPAD_UP.ordinal()] = Keys.I;
		this.buttons[GamePadButtons.SHIFT.ordinal()] = Keys.SHIFT_LEFT;
		this.buttons[GamePadButtons.ESC.ordinal()] = Keys.ESCAPE;
	}

	@Override
	public TextureRegion getTextureForButton(GamePadButtons gamepadButton) {
		int key = getButtonCode(gamepadButton);
		switch (key) {
		case Keys.K:
			return GlyphAndSymbols.k;
		case Keys.L:
			return GlyphAndSymbols.l;
		case Keys.J:
			return GlyphAndSymbols.j;
		case Keys.I:
			return GlyphAndSymbols.i;
		default:
			return GlyphAndSymbols.EMPTY;
		}
	}
}
