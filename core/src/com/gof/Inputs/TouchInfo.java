package com.gof.inputs;

import com.badlogic.gdx.math.Vector2;

public class TouchInfo {
	
	public static boolean aMovePadActiv = false;
	
	public Vector2 lastPos = new Vector2(0,0);
	public Vector2 startPos = new Vector2(0,0);
	public boolean touched = false;
    public boolean movePad = false;
    
    public Vector2 getVelocity(){
    	return new Vector2(lastPos.x - startPos.x, lastPos.y - startPos.y);
    }
    
    public float getDistance(){
    	return lastPos.dst(startPos);
    }
    
    public float getVelocityAngle(){
    	return getVelocity().angle();
    }
    
    public double getVelocityAngleRadian(){
    	return getVelocity().angle()* Math.PI / 180;
    }
    
    public double getXVelocityPercentage(){
    	return Math.cos(getVelocityAngleRadian());
    }
    
    public double getYVelocityPercentage(){
    	return Math.sin(getVelocityAngleRadian());
    }
    
}
