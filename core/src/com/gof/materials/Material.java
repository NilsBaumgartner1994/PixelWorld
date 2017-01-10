package com.gof.materials;

import com.badlogic.gdx.graphics.Texture;
import com.gof.game.ResourceLoader;
import com.gof.nature.Nature;

public class Material{
	
	public String texture;
	
	public Texture getTexture(){
		if(texture==null) return ResourceLoader.getInstance().getTile("error");
		return ResourceLoader.getInstance().getTile(texture);
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
