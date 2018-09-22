package com.gentlemansoftware.pixelworld.profiles;

public class UserSoundProfile extends UserVariableProfile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207593096419342158L;

	public VarHolder<Float> uiSound;
	public VarHolder<Float> natureSound;

	public UserSoundProfile() {
		super("Sound Options");
		initVars();
	}

	private void initVars() {
		this.uiSound = new VarHolder<Float>(1f, "UI Sound");
		this.addVar(uiSound);
		this.natureSound = new VarHolder<Float>(1f, "Nature Sound");
		this.addVar(natureSound);
	}

}
