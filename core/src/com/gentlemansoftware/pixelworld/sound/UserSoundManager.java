package com.gentlemansoftware.pixelworld.sound;

import com.badlogic.gdx.audio.Sound;
import com.gentlemansoftware.pixelworld.profiles.UserSoundProfile;

public class UserSoundManager {

	UserSoundProfile profile;

	public UserSoundManager(UserSoundProfile profile) {
		setSoundProfile(profile);
	}

	public long playSound(EasySounds sound, float volume) {
		volume = volume > 1 ? 1 : volume;
		float multiplier = getMultiplier(sound);
		return SoundManager.getInstance().playSound(sound, volume * multiplier);
	}

	public void setSoundProfile(UserSoundProfile profile) {
		this.profile = profile;
	}

	private float getMultiplier(EasySounds sound) {
		float volume = 1f;
		if (EasySounds.isUISound(sound)) {
			return profile.uiSound.getVar();
		}
		if (EasySounds.isNatureSound(sound)) {
			return profile.natureSound.getVar();
		}
		if(EasySounds.isEntitySound(sound)){
			return profile.entitySound.getVar();
		}

		return volume;
	}

}
