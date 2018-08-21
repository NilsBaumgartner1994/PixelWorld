package com.gof.game;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class SaveAndLoadable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5906416137622355877L;
	
	private static <T> T decode(String content, Class<T> class1){
		Json json = new Json();	
		content = Base64Coder.decodeString( content );
		T instance = json.fromJson( class1, content);
		return instance;
	}
	
	private String encode(){
		Json json = new Json();

		String data = json.toJson(this);
		data = Base64Coder.encodeString( data );
		return data;
	}
	
	/**
	 * Internal
	 */
	
	public static <T> T loadFromInternal(String path, Class<T> class1){
		FileController c = FileController.getInstance();
		String content = c.readLocalFile(path);
		return decode(content,class1);
	}
	
	public void saveToInternal(String path){
		FileController c = FileController.getInstance();
		String serialized = encode();
		c.writeLocalFile(path, serialized);
	}
	
	/**
	 * External
	 */
	
	public static <T> T loadFromExternal(String path, Class<T> class1){
		FileController c = FileController.getInstance();
		String content = c.readExternalFile(path);
		return decode(content,class1);
	}
	
	public void saveToExternal(String path){
		FileController c = FileController.getInstance();
		String serialized = encode();
		c.writeExternalFile(path, serialized);
	}
	
	
	
}
