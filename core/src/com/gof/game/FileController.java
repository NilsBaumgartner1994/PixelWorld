package com.gof.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileController {
	
	/**
	 * Local    - if you need to write small files, e.g. save a game state
	 * Internal - read-only // all the assets (images, audio files, etc.)
	 * External - a user can remove it or delete the files you wrote
	 */

	public static FileController instance;

	public FileController() {
		instance = this;
	}

	public static FileController getInstance() {
		return instance;
	}
	
	/**
	 * Local
	 */
	public void writeLocalFile(String path, String content){
		writeLocalFile(path,content, false);
	}
	
	public void writeLocalFile(String path, String content, boolean appendOnly){
		FileHandle file = Gdx.files.local(path);
		file.writeString(content, appendOnly);
	}
	
	public String readLocalFile(String path){
		FileHandle file = Gdx.files.local(path);
		return file.readString();
	}
	
	/**
	 * External
	 */
	public void writeExternalFile(String path, String content){
		writeExternalFile(path,content, false);
	}
	
	public void writeExternalFile(String path, String content, boolean appendOnly){
		FileHandle file = Gdx.files.external(path);
		file.writeString(content, appendOnly);
	}
	
	public String readExternalFile(String path){
		FileHandle file = Gdx.files.external(path);
		return file.readString();
	}
	
	
	
	

	

}
