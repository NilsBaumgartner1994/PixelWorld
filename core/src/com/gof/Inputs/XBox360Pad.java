package com.gof.Inputs;

import com.badlogic.gdx.controllers.PovDirection;

//This code was taken from http://www.java-gaming.org/index.php?topic=29223.0
//With thanks that is!

public class XBox360Pad {
	/*
	 * It seems there are different versions of gamepads with different ID
	 * Strings. Therefore its IMO a better bet to check for: if
	 * (controller.getName().toLowerCase().contains("xbox") &&
	 * controller.getName().contains("360"))
	 *
	 * Controller (Gamepad for Xbox 360) Controller (XBOX 360 For Windows)
	 * Controller (Xbox 360 Wireless Receiver for Windows) Controller (Xbox
	 * wireless receiver for windows) XBOX 360 For Windows (Controller) Xbox 360
	 * Wireless Receiver Xbox Receiver for Windows (Wireless Controller) Xbox
	 * wireless receiver for windows (Controller)
	 */
	// public static final String ID = "XBOX 360 For Windows (Controller)";
	
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
	
	public static final int AXIS_LEFT_X = 1; // -1 is left | +1 is right
	public static final int AXIS_LEFT_Y = 0; // -1 is up | +1 is down
	public static final int AXIS_LEFT_TRIGGER = 4; // value 0 to 1f
	public static final int AXIS_RIGHT_X = 3; // -1 is left | +1 is right
	public static final int AXIS_RIGHT_Y = 2; // -1 is up | +1 is down
	public static final int AXIS_RIGHT_TRIGGER = 4; // value 0 to -1f
	
	public static String getButton(int buttonCode){
		switch(buttonCode){
		case 0 : return "A";
		case 1 : return "B";
		case 2 : return "X";
		case 3 : return "Y";
		case 4 : return "LB";
		case 5 : return "RB";
		case 6 : return "Back";
		case 7 : return "Start";
		case 8 : return "Left Stick";
		case 9 : return "Right Stick";
		}
		return "Unkown Button";
	}
	
	public static int getButton(String buttonName){
		switch(buttonName){
		case "A" : return 0;
		case "B" : return 1;
		case "X" : return 2;
		case "Y" : return 3;
		case "LB" : return 4;
		case "RB" : return 5;
		case "Back" : return 6;
		case "Start" : return 7;
		case "Left Stick" : return 8;
		case "Right Stick" : return 9;
		}
		return -1;
	}
}
