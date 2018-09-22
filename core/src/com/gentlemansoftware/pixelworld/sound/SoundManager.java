package com.gentlemansoftware.pixelworld.sound;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	public static SoundManager instance;
	
	private Map<EasySounds,Sound> soundMap;

	public SoundManager() {
		instance = this;
		initSounds();
	}

	public static SoundManager getInstance() {
		return instance;
	}
	
	private void initSounds(){
		soundMap = new HashMap<EasySounds,Sound>();
		soundMap.put(EasySounds.CLICK, createSound("click1.ogg"));
		soundMap.put(EasySounds.STEP, createSound("footstep01.ogg"));
	}
	
	private Sound getSound(EasySounds sound){
		return soundMap.get(sound);
	}
	
	public void playSound(EasySounds sound, float volume){
		Sound s = getSound(sound);
		if(s != null){
			s.play(volume);
		}
	}
	
	private static final String soundPath = "data/sounds/";
	
	private Sound createSound(String name){
		return Gdx.audio.newSound(Gdx.files.internal(soundPath+name));
	}

	public void disposeAll() {
		for(Sound s : soundMap.values()){
			s.dispose();
		}
	}

}
