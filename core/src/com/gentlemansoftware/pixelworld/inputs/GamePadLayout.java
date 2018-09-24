package com.gentlemansoftware.pixelworld.inputs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gentlemansoftware.pixelworld.menuComponents.GlyphAndSymbols;

public class GamePadLayout {

	int[] buttons = new int[GamePadButtons.values().length];

	public GamePadLayout() {
		
	}

	public GamePadButtons getButton(int buttonCode) {
		for(int i=0; i<GamePadButtons.values().length;i++){
			if(buttons[i]==buttonCode){
				return GamePadButtons.values()[i];
			}
		}
		
		return GamePadButtons.UNKOWN;
	}
	
	public int getButtonCode(GamePadButtons gamepadbutton){
		return this.buttons[gamepadbutton.ordinal()];
	}
	
	public TextureRegion getTextureForButton(GamePadButtons gamepadButton) {
		return GlyphAndSymbols.EMPTY;
	}

}
