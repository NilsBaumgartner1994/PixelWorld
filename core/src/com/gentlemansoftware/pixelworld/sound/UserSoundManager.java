package com.gentlemansoftware.pixelworld.sound;

import com.badlogic.gdx.audio.Sound;
import com.gentlemansoftware.pixelworld.profiles.UserSoundProfile;

public class UserSoundManager {

	UserSoundProfile profile;

	public UserSoundManager(UserSoundProfile profile) {
		setSoundProfile(profile);
	}

	public void playSound(EasySounds sound) {
		float volume = getVolume(sound);
		System.out.println("Play at Volume: " + volume);
		SoundManager.getInstance().playSound(sound, volume);
	}

	public void setSoundProfile(UserSoundProfile profile) {
		this.profile = profile;
	}

	private float getVolume(EasySounds sound) {
		float volume = 1f;
		if (EasySounds.isUISound(sound)) {
			System.out.println("is UI Sound: "+profile.uiSound.getVar());
			return profile.uiSound.getVar();
		}
		if (EasySounds.isNatureSound(sound)) {
			System.out.println("is Nature Sound");
			return profile.natureSound.getVar();
		}

		return volume;
	}

}
