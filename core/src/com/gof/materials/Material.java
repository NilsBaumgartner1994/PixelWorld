package com.gof.materials;

import com.badlogic.gdx.graphics.Texture;
import com.gof.game.ResourceLoader;
import com.gof.nature.Nature;

public class Material extends Nature{
	
	@Override
	public Texture getTexture(){
		if(texture==null) return ResourceLoader.getInstance().getTile("error");
		return ResourceLoader.getInstance().getTile(texture);
	}	
}
