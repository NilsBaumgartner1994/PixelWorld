package com.gentlemansoftware.pixelworld.profiles;

import com.gentlemansoftware.pixelworld.game.SaveAndLoadable;

public class UserProfile extends SaveAndLoadable {

	public UserDebugProfile debugProfile;
	public UserSoundProfile soundProfile;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7248397811160277883L;
	public String name;

	public UserProfile() {
		this.name = "Default";
		this.debugProfile = new UserDebugProfile();
		this.soundProfile = new UserSoundProfile();
	}

	public static final transient String DATA = "data/";
	public static final transient String PROFILES = DATA + "profiles/";
	public static final transient String ENDING = ".profile";

	public static UserProfile load(String name) {
		return (UserProfile) SaveAndLoadable.loadFromInternal(PROFILES + name + ENDING, UserProfile.class);
	}

	public void save() {
		super.saveToInternal(PROFILES + name + ENDING);
	}

}
