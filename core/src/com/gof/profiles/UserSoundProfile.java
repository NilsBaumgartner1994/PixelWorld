package com.gof.profiles;

import java.io.Serializable;

public class UserSoundProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207593096419342158L;
	
	public Float uiSound;
	public Float natureSound;
	
	public UserSoundProfile(){
		this.uiSound = 1f;
		this.natureSound = 1f;
	}
	
	
	
}
