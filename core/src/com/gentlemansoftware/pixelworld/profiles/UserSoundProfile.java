package com.gentlemansoftware.pixelworld.profiles;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UserSoundProfile implements UserVariableProfile {

	public VarHolder<Float> masterVolume;
	public VarHolder<Float> uiSound;
	public VarHolder<Float> natureSound;
	public VarHolder<Float> entitySound;

	public UserSoundProfile() {
		initVars();
	}

	private void initVars() {
		this.masterVolume = new VarHolder<Float>(1f, "Master Volume");
		this.uiSound = new VarHolder<Float>(1f, "UI Sound");
		this.natureSound = new VarHolder<Float>(1f, "Nature Sound");
		this.entitySound = new VarHolder<Float>(1f, "Entity Sound");
	}
	
	@Override
	public List<VarHolder<? extends Serializable>> getVars(){
		List<VarHolder<?>> list = new LinkedList<VarHolder<?>>();
		list.add(masterVolume);
		list.add(uiSound);
		list.add(natureSound);
		list.add(entitySound);
		return list;
	}
	
	@Override
	public String getName(){
		return "Audio Options";
	}

}
