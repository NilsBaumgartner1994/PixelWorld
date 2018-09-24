package com.gentlemansoftware.pixelworld.inputs;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gentlemansoftware.pixelworld.menuComponents.GlyphAndSymbols;

//This code was taken from http://www.java-gaming.org/index.php?topic=29223.0
//With thanks that is!

public class GamePadLayoutPlaystation4 extends GamePadLayout {

	public static final int BUTTON_SQUARE = 0;
	public static final int BUTTON_X = 1;
	public static final int BUTTON_CIRCLE = 2;
	public static final int BUTTON_TRIANGLE = 3;
	public static final int BUTTON_L1 = 4;
	public static final int BUTTON_R1 = 5;
	public static final int BUTTON_L2 = 6;
	public static final int BUTTON_R2 = 7;
	public static final int BUTTON_SHARE = 8;
	public static final int BUTTON_START = 9;
	public static final int BUTTON_L3 = 10;
	public static final int BUTTON_R3 = 11;
	public static final int BUTTON_PS4 = 12;
	public static final int BUTTON_MIDDLEPAD = 13;

	public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
	public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
	public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
	public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;

	public static final int AXIS_LEFT_X = 1; // -1 is left | +1 is right
	public static final int AXIS_LEFT_Y = 0; // -1 is up | +1 is down
	public static final int AXIS_LEFT_TRIGGER = 4; // value 0 to 1f
	public static final int AXIS_RIGHT_X = 3; // -1 is left | +1 is right
	public static final int AXIS_RIGHT_Y = 2; // -1 is up | +1 is down
	public static final int AXIS_RIGHT_TRIGGER = 4; // value 0 to -1f

	public GamePadLayoutPlaystation4() {
		this.buttons[GamePadButtons.BACK.ordinal()] = BUTTON_SHARE;
		this.buttons[GamePadButtons.START.ordinal()] = BUTTON_START;
		this.buttons[GamePadButtons.RIGHTPAD_DOWN.ordinal()] = BUTTON_X;
		this.buttons[GamePadButtons.RIGHTPAD_RIGHT.ordinal()] = BUTTON_CIRCLE;
		this.buttons[GamePadButtons.RIGHTPAD_LEFT.ordinal()] = BUTTON_SQUARE;
		this.buttons[GamePadButtons.RIGHTPAD_UP.ordinal()] = BUTTON_TRIANGLE;
		this.buttons[GamePadButtons.SHIFT.ordinal()] = BUTTON_L1;
		this.buttons[GamePadButtons.ESC.ordinal()] = BUTTON_START;
	}

	@Override
	public TextureRegion getTextureForButton(GamePadButtons gamepadButton) {
		int key = getButtonCode(gamepadButton);
		switch (key) {
		case BUTTON_X:
			return GlyphAndSymbols.PlaystationSquare;
		case BUTTON_CIRCLE:
			return GlyphAndSymbols.PlaystationCircle;
		case BUTTON_TRIANGLE:
			return GlyphAndSymbols.PlaystationTriangle;
		case BUTTON_SQUARE:
			return GlyphAndSymbols.PlaystationSquare;
		default:
			return GlyphAndSymbols.EMPTY;
		}
	}
}
