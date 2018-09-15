package com.gof.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.Model;

public class ResourceLoader {

	public AssetManager assets;
	
	public static ResourceLoader instance;

	public ResourceLoader() {
		instance = this;
		assets = Main.getInstance().assets;
	}

	public static ResourceLoader getInstance() {
		return instance;
	}

	public static String data = "data/";
	
	

	public static String tiles = data + "tiles/";
	public static String nature = data + "nature/";
	public static String entitys = data + "entitys/";
	public static String gui = data + "gui/";
	public static String controlls = gui+"controlls/";
	public static String icons = data + "icons/";
	public static final String textureEnding = ".png";

	public static String shaddows = entitys + "shaddows/";
	public static String player = entitys + "player.png";
	public static final String modelEnding = ".g3db";

	private void loadAsset(String path, Class class1) {
		assets.load(Gdx.files.internal(path).path(), class1);
		float progress = 0;
		while (!assets.update()) {
			if (progress != assets.getProgress()) {
				progress = assets.getProgress();
				Main.log(getClass(), "Loaded: " + assets.getProgress() * 100 + "%");
			}
		}
	}
	
	/**
	 * Texture
	 */

	public Texture getTexture(String path) {
		if (!assets.isLoaded(path)) {
			loadAsset(path,Texture.class);
		}
		return assets.get(path);
	}
	
	public Texture getGUI(String name) {
		return getTexture(gui + name + ".png");
	}

	public Texture getIcon(String name) {
		return getTexture(icons + name + ".png");
	}
	
	public Texture getEntity(String entityType, String name){
		return getTexture(entitys+entityType+"/"+name+".png");
	}
	
	public Texture getTile(String name) {
		return getTexture(tiles + name + ".png");
	}
	
	public Texture getNature(String name) {
		return getTexture(nature + name + ".png");
	}
	
	/**
	 * Model
	 */

	public Model getModel(String path) {
		if (!assets.isLoaded(path)) {
			loadAsset(path,Model.class);
		}
		return assets.get(path);
	}
	
	public Model getBlock(String name){
		return getModel(tiles+name+modelEnding);
	}
	
}
