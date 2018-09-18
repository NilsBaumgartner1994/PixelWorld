package com.gentlemansoftware.pixelworld.menuComponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;

public class GlyphAndSymbols {

	private final static int circleSize = 18; 
	private static Texture glyph = getGlyphTexture();
	
	public static TextureRegion XboxX = getGlyph(0,3);
	public static TextureRegion XboxY = getGlyph(1,3);
	public static TextureRegion XboxA = getGlyph(2,3);
	public static TextureRegion XboxB = getGlyph(3,3);
	
	public static TextureRegion PlaystationSquare = getGlyph(0,2);
	public static TextureRegion PlaystationTriangle = getGlyph(1,2);
	public static TextureRegion PlaystationCircle = getGlyph(2,2);
	public static TextureRegion PlaystationX = getGlyph(3,2);
	
	public static TextureRegion EMPTY = getGlyph(13,1);
	
	
	public static TextureRegion a = getGlyph(0,0);
	public static TextureRegion b = getGlyph(1,0);
	public static TextureRegion c = getGlyph(2,0);
	public static TextureRegion d = getGlyph(3,0);
	public static TextureRegion e = getGlyph(3,0);
	public static TextureRegion f = getGlyph(5,0);
	public static TextureRegion g = getGlyph(6,0);
	public static TextureRegion h = getGlyph(7,0);
	public static TextureRegion i = getGlyph(8,0);
	public static TextureRegion j = getGlyph(9,0);
	public static TextureRegion k = getGlyph(10,0);
	public static TextureRegion l = getGlyph(11,0);
	public static TextureRegion m = getGlyph(12,0);
	public static TextureRegion n = getGlyph(13,0);
	public static TextureRegion o = getGlyph(0,1);
	public static TextureRegion p = getGlyph(0,2);
	public static TextureRegion q = getGlyph(0,3);
	public static TextureRegion r = getGlyph(0,4);
	public static TextureRegion s = getGlyph(0,5);
	public static TextureRegion t = getGlyph(0,6);
	public static TextureRegion u = getGlyph(0,7);
	public static TextureRegion v = getGlyph(0,8);
	public static TextureRegion w = getGlyph(0,9);
	public static TextureRegion x = getGlyph(0,10);
	public static TextureRegion y = getGlyph(0,11);
	public static TextureRegion z = getGlyph(0,12);
	
	
	private static TextureRegion getGlyph(int x, int y){
		return new TextureRegion(glyph, x*circleSize, y*circleSize, circleSize, circleSize);
	}

	private static Texture getGlyphTexture() {
		return ResourceLoader.getInstance().getTexture(ResourceLoader.controlls + "myGlyph.png");
	}

}
