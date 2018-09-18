package com.gentlemansoftware.pixelworld.simplemenu;

import com.badlogic.gdx.graphics.Texture;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;

public enum SimpleMenuNameTypes {

	MAIN, SUB;
	
	public static Texture getTexture(SimpleMenuNameTypes type){
		switch(type){
		case MAIN : return ResourceLoader.getInstance().getGUI("menus/menu_title");
		case SUB : return ResourceLoader.getInstance().getGUI("menus/menu_information_top");
		default : return getTexture(MAIN);
		}
	}

}
