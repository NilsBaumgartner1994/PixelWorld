package com.gof.profiles;

import java.io.Serializable;

public class UserSoundProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207593096419342158L;
	
	public VarHolder<Float> uiSound;
	public VarHolder<Float> natureSound;
	
	public UserSoundProfile(){
		this.uiSound = new VarHolder<Float>(1f);
		this.natureSound = new VarHolder<Float>(1f);
	}
	
	
	
}
