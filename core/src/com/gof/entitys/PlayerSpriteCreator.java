package com.gof.entitys;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gof.game.ResourceLoader;
import com.gof.physics.Direction;

public class PlayerSpriteCreator{

	public static List<Sprite> getPlayerSprite(LocalPlayer p){
//		int dir = p.direction;
//		boolean east = dir==Direction.EAST;
//		if(east) dir=Direction.WEST;
//		
//		Texture body = ResourceLoader.getInstance().getTexture(ResourceLoader.entitys+"body/"+dir+"-0.png");
//		Texture head = ResourceLoader.getInstance().getTexture(ResourceLoader.entitys+"head/"+dir+".png");
//				
//		Sprite sbody = new Sprite(body);
//		Sprite shead = new Sprite(head);
//		
//		
//		if(east){
//			sbody.flip(true, false);
//			shead.flip(true, false);
//		}		
				
		List<Sprite> back = new ArrayList<Sprite>();
		
		Sprite shaddow = new Sprite(ResourceLoader.getInstance().getShaddow("entity"));
		back.add(shaddow);
		
//		back.add(sbody);
//		back.add(shead);
		
		return back;
	}
	
}
