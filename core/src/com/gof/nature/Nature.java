package com.gof.nature;

import com.badlogic.gdx.graphics.Texture;
import com.gof.game.ResourceLoader;
import com.gof.materials.Material;

public class Nature extends Material{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4115895722414825191L;

	@Override
	public Texture getTexture(){
		if(texture==null) return ResourceLoader.getInstance().getNatrue("error");
		return ResourceLoader.getInstance().getNatrue(texture);
	}	
	
}
