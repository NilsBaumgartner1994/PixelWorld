package com.gentlemansoftware.pixelworld.helper;

import com.badlogic.gdx.graphics.Color;

public class EasyColor extends Color {

	public static final Color TRANSPARENT = getColorFrom255RGB(0, 0, 0, 0);

	public static final Color GREENLIGHT = getColorFrom255RGB(154, 235, 0);
	public static final Color GREENDARK = getColorFrom255RGB(81, 162, 0);

	public static final Color BLUELIGHT = getColorFrom255RGB(65, 146, 195);
	public static final Color BLUEDARK = getColorFrom255RGB(48, 81, 130);

	public static final Color YELLOWLIGHT = getColorFrom255RGB(235, 211, 32);
	public static final Color YELLOWDARK = getColorFrom255RGB(138, 138, 0);

	public static final Color REDLIGHT = getColorFrom255RGB(227, 81, 0);
	public static final Color REDDARK = getColorFrom255RGB(162, 48, 0);

	public static final Color REDACTIVE = getColorFrom255RGB(172, 16, 48);

	public static final Color PLAYSTATION_TRIANGLE = getColorFromHex("#59C2C8");
	public static final Color PLAYSTATION_CIRCLE = getColorFromHex("#E18281");
	public static final Color PLAYSTATION_SQUARE = getColorFromHex("#C6A4C8");
	public static final Color PLAYSTATION_X = getColorFromHex("#94AFD3");
	public static final Color PLAYSTATION_BUTTONBACKGROUND = getColorFromHex("#45495A");

	public static final Color XBOX_X = getColorFromHex("#5A85BB");
	public static final Color XBOX_Y = getColorFromHex("#F2DD5B");
	public static final Color XBOX_A = getColorFromHex("#6A9E54");
	public static final Color XBOX_B = getColorFromHex("#C33B3C");
	public static final Color XBOX_BUTTONBACKGROUND = getColorFromHex("#181817");

	public static Color getColorFrom255RGB(int r, int g, int b, int a) {
		return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
	}

	public static Color getColorFromHex(String colorStr) {
		int r = Integer.valueOf(colorStr.substring(1, 3), 16);
		int g = Integer.valueOf(colorStr.substring(3, 5), 16);
		int b = Integer.valueOf(colorStr.substring(5, 7), 16);

		int a = 255;
		if (colorStr.length() == 9) {
			a = Integer.valueOf(colorStr.substring(7, 9), 16);
		}

		return getColorFrom255RGB(r, g, b, a);
	}

	public static Color getColorFrom255RGB(int r, int g, int b) {
		return getColorFrom255RGB(r, g, b, 255);
	}

}
