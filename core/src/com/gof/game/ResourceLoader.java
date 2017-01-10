package com.gof.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class ResourceLoader {

	public AssetManager assets;

	public static ResourceLoader instance;

	public ResourceLoader() {
		instance = this;
		assets = new AssetManager();
	}

	public static ResourceLoader getInstance() {
		return instance;
	}

	public static String data = "data/";
	public static String entitys = data + "entitys/";
	public static String shaddows = entitys + "shaddows/";
	public static String player = entitys + "player.png";

	public static String tiles = data + "tiles/";
	public static String nature = data + "nature/";
	public static String gui = data + "gui/";
	public static String icons = data + "icons/";

	public void addToLoad(String name) {
		assets.load(Gdx.files.internal(name).path(), Texture.class);
	}

	private void loadAsset(String name) {
		addToLoad(name);
		float progress = 0;
//		Main.log(getClass(), "Loading: " + name);
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

	boolean debug = false;

	public Texture getTile(String name) {
		if (debug)
			return getTexture(tiles + "debug/" + name + ".png");
		return getTexture(tiles + name + ".png");
	}

	public Texture getNatrue(String name) {
		return getTexture(nature + name + ".png");
	}

	public Texture getUI(String name) {
		return getTexture(gui + name + ".png");
	}

	public Texture getShaddow(String name) {
		return getTexture(shaddows + name + ".png");
	}

	public Texture getIcon(String name) {
		return getTexture(icons + name + ".png");
	}

	public Texture getCloudShaddow() {
		return getTexture(data + "shaddow/cloud.png");
	}

}
