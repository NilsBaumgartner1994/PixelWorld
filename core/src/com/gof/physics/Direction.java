package com.gof.physics;

import com.badlogic.gdx.math.Vector2;

public class Direction {

	static int speed = 1;

	public static final Position STOP = new Position(0,0,0,0);
	
	public static final Position NORTH = new Position(0,0,0,speed);
	public static final Position EAST = new Position(0,2*speed,0,0);
	public static final Position SOUTH = new Position(0,0,0,-speed);
	public static final Position WEST = new Position(0,-2*speed,0,0);
	
	
}