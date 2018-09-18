package com.gentlemansoftware.pixelworld.helper;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteHelper {

	public static Sprite setToWidth(Sprite image, int Towidth){
		int width = image.getRegionWidth();
		float scaleFactor = (Towidth*1f) / (1f*width);
		image.setOrigin(0, image.getHeight());
		image.setScale(scaleFactor);
		
		return image;
	}

}
