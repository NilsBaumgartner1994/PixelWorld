package com.gof.physics;

import com.gof.game.Main;

public class Direction {

	static int speed = 1;

	public static final Position STOP = new Position(0,0,0,0);
	
	public static final Position NORTH = new Position(0,0,0,speed);
	public static final Position EAST = new Position(0,2*speed,0,0);
	public static final Position SOUTH = new Position(0,0,0,-speed);
	public static final Position WEST = new Position(0,-2*speed,0,0);
	
	public static Position getDirection(Position from, Position to){
		Position distance = from.distance(to);
		
		if(distance.length()<NORTH.length()){
			return distance;
		}
		
		Position dxn = distance.cpy().addAndSet(NORTH);
		Position dxe = distance.cpy().addAndSet(EAST);
		Position dxs = distance.cpy().addAndSet(SOUTH);
		Position dxw = distance.cpy().addAndSet(WEST);

		float dxlN = dxn.length();
		float dxlE = dxe.length();
		float dxlS = dxs.length();
		float dxlW = dxw.length();
		
		float minNE = Float.min(dxlN, dxlE);
		float minSW = Float.min(dxlS, dxlW);
		float minNESW = Float.min(minNE, minSW);
		
		if(dxlN==minNESW) return NORTH.cpy();
		if(dxlE==minNESW) return EAST.cpy();
		if(dxlS==minNESW) return SOUTH.cpy();
		if(dxlW==minNESW) return WEST.cpy();
		
		return STOP.cpy();
	}
	
	public static Position clambIfDistanceToLong(Position from, Position to, Position velocity){
		Position distance = to.distance(from);
		if(distance.length()<velocity.length()){
			return distance;
		}
		return velocity;
	}
	
	
}