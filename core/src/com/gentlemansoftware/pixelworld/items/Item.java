package com.gentlemansoftware.pixelworld.items;

import com.gentlemansoftware.pixelworld.materials.MyMaterial;

public class Item extends AbstractItem{
	
	MyMaterial material;
	
	public Item(MyMaterial m, int amount){
		super(amount);
		this.material = m;
	}
	
	public Item(MyMaterial m){
		this(m,1);
	}
	
	public MyMaterial getMaterial(){
		return this.material;
	}
	
	@Override	
	public String getIconName(){
		if(this.material==null) super.getIconName();
		return this.material.getName();
	}
	
	
	
}
