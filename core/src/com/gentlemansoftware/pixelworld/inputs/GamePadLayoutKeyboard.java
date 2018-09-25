package com.gentlemansoftware.pixelworld.inputs;

import com.badlogic.gdx.Input.Keys;
import com.gentlemansoftware.pixelworld.helper.EasyColor;
import com.gentlemansoftware.pixelworld.menuComponents.GlyphAndSymbols;

//This code was taken from http://www.java-gaming.org/index.php?topic=29223.0
//With thanks that is!

public class GamePadLayoutKeyboard extends GamePadLayout {

	public GamePadLayoutKeyboard() {
		this.setEntry(GamePadButtons.BACK, Keys.BACK, GlyphAndSymbols.EMPTY, EasyColor.WHITE);
		this.setEntry(GamePadButtons.RIGHTPAD_DOWN, Keys.K, GlyphAndSymbols.k, EasyColor.BLUELIGHT);
		this.setEntry(GamePadButtons.RIGHTPAD_LEFT, Keys.J, GlyphAndSymbols.j, EasyColor.GREENLIGHT);
		this.setEntry(GamePadButtons.RIGHTPAD_RIGHT, Keys.L, GlyphAndSymbols.l, EasyColor.REDLIGHT);
		this.setEntry(GamePadButtons.RIGHTPAD_UP, Keys.I, GlyphAndSymbols.i, EasyColor.YELLOWLIGHT);
	}

}
