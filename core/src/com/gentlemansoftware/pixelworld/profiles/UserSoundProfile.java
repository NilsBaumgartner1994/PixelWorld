package com.gentlemansoftware.pixelworld.profiles;

import java.io.Serializable;

public class UserSoundProfile implements Serializable, UserVariableProfileInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207593096419342158L;

	public VarHolder<Float> uiSound;
	public VarHolder<Float> natureSound;

	public UserSoundProfile() {
		this.uiSound = new VarHolder<Float>(1f,"UI Sound");
		this.natureSound = new VarHolder<Float>(1f,"Nature Sound");
	}

	@Override
	public VarHolder<?>[] getVars() {
		return new VarHolder[] { uiSound, natureSound };
	}

}
