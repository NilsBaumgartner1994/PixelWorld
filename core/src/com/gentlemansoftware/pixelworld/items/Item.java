package com.gentlemansoftware.pixelworld.items;

import com.gentlemansoftware.pixelworld.materials.MyMaterial;
import com.gentlemansoftware.pixelworld.nature.Nature;

public class Item extends AbstractItem{
	
	MyMaterial material;
	
	public Item(MyMaterial m, int amount){
		super(amount);
		this.material = m;
	}
	
	public Item(MyMaterial m){
		this(m,1);
	}
		
	public boolean isNature(){
		if(material==null) return false;
		if(material instanceof Nature) return true;
		return false;
	}
	
	public Nature getNature(){
		return (Nature) material;
	}
	
	public MyMaterial getMaterial(){
		return this.material;
	}
	
	@Override	
	public String getIconName(){
		if(this.material.texture==null) super.getIconName();
		return this.material.texture;
	}
	
	
	
}
