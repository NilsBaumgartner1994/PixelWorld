package com.redagent.materials;

import com.badlogic.gdx.graphics.Texture;
import com.redagent.game.ResourceLoader;
import com.redagent.nature.Nature;

public class Material extends Nature{
	
	@Override
	public Texture getTexture(){
		if(texture==null) return ResourceLoader.getInstance().getTile("error");
		return ResourceLoader.getInstance().getTile(texture);
	}	
}
