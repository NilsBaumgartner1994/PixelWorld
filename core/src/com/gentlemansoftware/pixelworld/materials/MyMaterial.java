package com.gentlemansoftware.pixelworld.materials;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;

public class MyMaterial implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6591862422992199495L;
	private byte id;
	
	public MyMaterial(Byte id){
		this.id = id;
	}
	
	public static final transient MyMaterial STONE = new MyMaterial((byte) 1);
	public static final transient Integer STONEHEIGHT = 40;
	public static final transient MyMaterial GRASS = new MyMaterial((byte) 2);
	public static final transient Integer GRASSHEIGHT = 0;
	public static final transient MyMaterial DIRT = new MyMaterial((byte) 3);
	public static final transient Integer DIRTHEIGHT = -10;
	public static final transient MyMaterial WATER = new MyMaterial((byte) 8);
	public static final transient Integer WATERHEIGHT = -20;
	public static final transient MyMaterial SAND = new MyMaterial((byte) 12);
	public static final transient Integer SANDHEIGHT = -10;
	
	public static final transient MyMaterial DEBUG = new MyMaterial((byte) 127);
	public static final transient MyMaterial ERROR = new MyMaterial((byte) -128);

	private final static transient Map<Byte,String> materialList = new HashMap<Byte,String>();
	private final static transient Map<Byte,String> addonMaterialList = new HashMap<Byte,String>();
	static {
		materialList.put(STONE.getID(), "stone");
		materialList.put(GRASS.getID(), "grass");
		materialList.put(WATER.getID(), "water");
		materialList.put(SAND.getID(), "sand");
	}
	
	private final static transient Map<Byte,Integer> materialHeightList = new HashMap<Byte,Integer>();
	static {
		materialHeightList.put(STONE.getID(), STONEHEIGHT);
		materialHeightList.put(GRASS.getID(), GRASSHEIGHT);
		materialHeightList.put(WATER.getID(), WATERHEIGHT);
		materialHeightList.put(SAND.getID(), SANDHEIGHT);
	}
	
	public static int getDefaultHeightByID(Byte id){
		return materialHeightList.get(id);
	}
	
	private static String getNameByID(Byte id) {
		if(materialList.containsKey(id)){
			return materialList.get(id);
		}
		
		if(addonMaterialList.containsKey(id)){
			return addonMaterialList.get(id);
		}
		
		return null;
	}
	
	public static boolean registerNewModelWithID(Byte id,String name){
		if(materialList.containsKey(id)){
			return false;
		}
		if(addonMaterialList.containsKey(id)){
			return false;
		}
		
		addonMaterialList.put(id, name);
		
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
	
	public String getName(){
		return getNameByID(getID());
	}
	
	public static Texture getTextureByID(byte id){
		String name = getNameByID(id);
		if(name==null) return null;
		return ResourceLoader.getInstance().getTile(name);
	}
	
	public boolean equals(MyMaterial m) {
		return getID() == m.getID();
	}

	
}
