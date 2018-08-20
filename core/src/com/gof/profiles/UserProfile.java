package com.gof.profiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.gof.game.Main;

public class UserProfile {

	private String name;

	public UserProfile() {
		this.name = "Default";
	}
	
	public static UserProfile load(String name){
		FileHandle file = Gdx.files.external(profiles+name+".profile");
		String text = file.readString();
	}

	public static UserProfile getDefaultUserProfile() {
		UserProfile p = new UserProfile();
		return p;
	}

	public static String data = "data/";
	public static String profiles = data+"profiles/";

	
	private void loadAsset(String name) {
		addToLoad(name);
		float progress = 0;
		// Main.log(getClass(), "Loading: " + name);
		while (!assets.update()) {
			if (progress != assets.getProgress()) {
				progress = assets.getProgress();
				Main.log(getClass(), "Loaded: " + assets.getProgress() * 100 + "%");
			}
		}
	}

	public Texture getTexture(String name) {
		if (!assets.isLoaded(name)) {
			loadAsset(name);
		}
		return assets.get(name);
	}

	public Texture getTile(String name) {
		if (debug)
			return getTexture(tiles + "debug/" + name + ".png");
		return getTexture(tiles + name + ".png");
	}

}
