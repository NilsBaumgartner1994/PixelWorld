package com.gof.items;

import com.badlogic.gdx.graphics.Texture;
import com.gof.game.ResourceLoader;
import com.gof.materials.MyMaterial;
import com.gof.nature.Nature;

public class AbstractItem {
	
	public final int maxItemStackAmount = 16;
	int amount;
	
	public AbstractItem(int amount){
		this.amount = amount>maxItemStackAmount ? maxItemStackAmount : amount;
	}
	
	public AbstractItem(){
		this(1);
	}
		
	public Texture getTexture(){
		return ResourceLoader.getInstance().getIcon(getIconName());
	}
	
	public String getIconName(){
		return "error";
	}
	
	public String toString(){
		return getIconName();
	}
	
}
