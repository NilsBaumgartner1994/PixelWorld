package com.gof.entitys;

import com.badlogic.gdx.graphics.Texture;
import com.gof.game.ResourceLoader;
import com.gof.physics.WorldTime;

public class BatSpriteAnimations {
	
	private static final String ENTITYTYPENAME = "bat";

	public static Texture getTexture(MotionState motion,WorldTime time){
		switch(motion){
		case STOP : return getSittingTexture(time);
		case MOVING : break;
		}
		return getTexture("bat_fly-0");
	}
	
	private static float sittingAnimationTime = .5f;
	private static Texture getSittingTexture(WorldTime time){
		float animationTimeInTicks = time.getTicksPerSecond()*sittingAnimationTime;
		float percentage = (time.getTicks()%(animationTimeInTicks))/animationTimeInTicks;
		if(percentage<0.25f) return getTexture("bat_fly-0");
		if(percentage<0.5f) return getTexture("bat_fly-1");
		if(percentage<0.75f) return getTexture("bat_fly-2");
		return getTexture("bat_fly-3");
	}
	
	private static Texture getTexture(String name){
		return ResourceLoader.getInstance().getEntity(ENTITYTYPENAME,name); 
	}

}
