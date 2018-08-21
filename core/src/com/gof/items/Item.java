package com.gof.items;

import com.gof.materials.Material;
import com.gof.nature.Nature;

public class Item extends AbstractItem{
	
	Material material;
	
	public Item(Material m, int amount){
		super(amount);
		this.material = m;
	}
	
	public Item(Material m){
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
	
	public Material getMaterial(){
		return this.material;
	}
	
	@Override	
	public String getIconName(){
		if(this.material.texture==null) super.getIconName();
		return this.material.texture;
	}
	
	
	
}
