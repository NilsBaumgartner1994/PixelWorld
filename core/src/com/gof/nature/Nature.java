package com.gof.nature;

import com.badlogic.gdx.graphics.Texture;
import com.gof.game.ResourceLoader;
import com.gof.materials.Material;

public class Nature extends Material{
	
	@Override
	public Texture getTexture(){
		if(texture==null) return ResourceLoader.getInstance().getNatrue("error");
		return ResourceLoader.getInstance().getNatrue(texture);
	}	
	
}
