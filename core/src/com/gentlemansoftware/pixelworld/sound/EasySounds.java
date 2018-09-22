package com.gentlemansoftware.pixelworld.sound;

public enum EasySounds {

	STEP, CLICK, PRESS, RELEASE, BAT_WING_FLAP;

	public static boolean isUISound(EasySounds sound) {
		switch (sound) {
		case CLICK:
		case PRESS:
		case RELEASE:
			return true;
		default:
			return false;
		}
	}
	
	public static boolean isNatureSound(EasySounds sound){
		switch (sound) {
		case STEP:
			return true;
		default:
			return false;
		}
	}
	
	public static boolean isEntitySound(EasySounds sound){
		switch (sound) {
		case BAT_WING_FLAP:
			return true;
		default:
			return false;
		}
	}

}
