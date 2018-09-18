package com.gentlemansoftware.pixelworld.nature;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;

public class Nature implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6591862422992199495L;
	private byte id;
	
	public Nature(Byte id){
		this.id = id;
	}
	
	public static final transient Nature TREE = new Nature((byte) 1);
	public static final transient Nature TALLGRASS = new Nature((byte) 10);
	
	public static final transient Nature DEBUG = new Nature((byte) 127);
	public static final transient Nature ERROR = new Nature((byte) -128);

	private final static transient Map<Byte,String> natureList = new HashMap<Byte,String>();
	private final static transient Map<Byte,String> addonNatureList = new HashMap<Byte,String>();
	static {
		natureList.put(TREE.getID(), "tree");
		natureList.put(TALLGRASS.getID(), "tallGrass");
	}
	
	private static String getNameByID(Byte id) {
		if(natureList.containsKey(id)){
			return natureList.get(id);
		}
		
		if(addonNatureList.containsKey(id)){
			return addonNatureList.get(id);
		}
		
		return null;
	}
	
	public String getName(){
		return getNameByID(getID());
	}
	
	public static boolean registerNewModelWithID(Byte id,String name){
		if(natureList.containsKey(id)){
			return false;
		}
		if(addonNatureList.containsKey(id)){
			return false;
		}
		
		addonNatureList.put(id, name);
		
		return true;
	}
	
//	public Model getModel(){
//		return getModelByID(getID());
//	}
	
	public Texture getTexture(){
		return getTextureByID(getID());
	}
	
	public Byte getID(){
		return this.id;
	}
	
//	public static Model getModelByID(byte id){
//		String modelName = getNameByID(id);
//		if(modelName==null) return null;
//		return ResourceLoader.getInstance().getBlock(modelName);
//	}
	
	public static Texture getTextureByID(byte id){
		String name = getNameByID(id);
		if(name==null) return null;
		return ResourceLoader.getInstance().getNature(name);
	}
	
	public boolean equals(Nature m) {
		return getID() == m.getID();
	}

	
}
