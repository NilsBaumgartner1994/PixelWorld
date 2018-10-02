package com.gentlemansoftware.pixelworld.items;

import com.badlogic.gdx.graphics.Texture;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.materials.MyMaterial;

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
