package com.gof.nature;

import com.badlogic.gdx.graphics.Texture;
import com.gof.game.ResourceLoader;

public class Nature{
	
	public String texture;
	
	public Texture getTexture(){
		if(texture==null) return ResourceLoader.getInstance().getTile("error");
		return ResourceLoader.getInstance().getNatrue(texture);
	}
	
	public void setTexture(String tex){
		this.texture = tex;
	}
	
	public boolean isSame(Nature m){
		return this.getClass()==m.getClass();
	}
	
	public boolean isSame(Class<?> m){
		return this.getClass()==m;
	}
	
}
