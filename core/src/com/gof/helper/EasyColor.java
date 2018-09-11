package com.gof.helper;

import com.badlogic.gdx.graphics.Color;

public class EasyColor extends Color{

	public static final Color GREENLIGHT = getColorFrom255RGB(154,235,0);
	public static final Color GREENDARK = getColorFrom255RGB(81,162,0);
	
	public static final Color BLUELIGHT = getColorFrom255RGB(65,146,195);
	public static final Color BLUEDARK = getColorFrom255RGB(48,81,130);
	
	public static final Color YELLOWLIGHT = getColorFrom255RGB(235,211,32);
	public static final Color YELLOWDARK = getColorFrom255RGB(138,138,0);
	
	public static final Color REDLIGHT = getColorFrom255RGB(227,81,0);
	public static final Color REDDARK = getColorFrom255RGB(162,48,0);
	
	public static final Color REDACTIVE = getColorFrom255RGB(172,16,48);
	
	
	public static Color getColorFrom255RGB(int r, int g, int b, int a){
		return new Color(r/255f,g/255f,b/255f,a/255f);
	}
	
	public static Color getColorFrom255RGB(int r, int g, int b){
		return getColorFrom255RGB(r,g,b,255);
	}

}
