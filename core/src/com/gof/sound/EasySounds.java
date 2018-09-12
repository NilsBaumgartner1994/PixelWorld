package com.gof.sound;

public enum EasySounds {

	STEP, CLICK, PRESS, RELEASE;

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

}
