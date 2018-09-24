package com.gentlemansoftware.pixelworld.inputs;

import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gentlemansoftware.pixelworld.menuComponents.GlyphAndSymbols;

//This code was taken from http://www.java-gaming.org/index.php?topic=29223.0
//With thanks that is!

public class GamePadLayoutXBox360 extends GamePadLayout {

	public static final int BUTTON_A = 0;
	public static final int BUTTON_B = 1;
	public static final int BUTTON_X = 2;
	public static final int BUTTON_Y = 3;
	public static final int BUTTON_LB = 4;
	public static final int BUTTON_RB = 5;
	public static final int BUTTON_BACK = 6;
	public static final int BUTTON_START = 7;
	public static final int BUTTON_L3 = 8;
	public static final int BUTTON_R3 = 9;

	public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
	public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
	public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
	public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;

	public GamePadLayoutXBox360() {
		this.buttons[GamePadButtons.BACK.ordinal()] = BUTTON_BACK;
		this.buttons[GamePadButtons.START.ordinal()] = BUTTON_START;
		this.buttons[GamePadButtons.RIGHTPAD_DOWN.ordinal()] = BUTTON_A;
		this.buttons[GamePadButtons.RIGHTPAD_RIGHT.ordinal()] = BUTTON_B;
		this.buttons[GamePadButtons.RIGHTPAD_LEFT.ordinal()] = BUTTON_X;
		this.buttons[GamePadButtons.RIGHTPAD_UP.ordinal()] = BUTTON_Y;
		this.buttons[GamePadButtons.SHIFT.ordinal()] = BUTTON_LB;
		this.buttons[GamePadButtons.ESC.ordinal()] = BUTTON_START;
	}
	
	@Override
	public TextureRegion getTextureForButton(GamePadButtons gamepadButton) {
		int key = getButtonCode(gamepadButton);
		switch (key) {
		case BUTTON_X:
			return GlyphAndSymbols.XboxX;
		case BUTTON_Y:
			return GlyphAndSymbols.XboxY;
		case BUTTON_A:
			return GlyphAndSymbols.XboxA;
		case BUTTON_B:
			return GlyphAndSymbols.XboxB;
		default:
			return GlyphAndSymbols.EMPTY;
		}
	}

	public static final int AXIS_LEFT_X = 1; // -1 is left | +1 is right
	public static final int AXIS_LEFT_Y = 0; // -1 is up | +1 is down
	public static final int AXIS_LEFT_TRIGGER = 4; // value 0 to 1f
	public static final int AXIS_RIGHT_X = 3; // -1 is left | +1 is right
	public static final int AXIS_RIGHT_Y = 2; // -1 is up | +1 is down
	public static final int AXIS_RIGHT_TRIGGER = 4; // value 0 to -1f

}
