package com.gof.physics;

import com.badlogic.gdx.math.Vector2;

public enum Direction {

	// MIDDLE, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST,
	// NORTHWEST;
	MIDDLE, NORTH, EAST, SOUTH, WEST;

	public static Direction getDirectionFromVector(Vector2 v) {
		float a = v.angle();

		if (a >= 45 && a <= 180 - 45)
			return NORTH;
		if (a > 180 - 45 && a < 180 + 45)
			return WEST;
		if (a >= 180 + 45 && a <= 360 - 45)
			return SOUTH;
		return EAST;
	}
	
	public static Direction rotateDirection(Direction dir, float deg){
		Vector2 vec = getVectorFromDirection(dir);
		vec.rotate(-deg);
		return getDirectionFromVector(vec);
	}

	private static Vector2 getVectorFromDirection(Direction dir) {
		switch (dir) {
		case MIDDLE:
			return new Vector2(0, 0);
		case NORTH:
			return new Vector2(0, 1);
		case EAST:
			return new Vector2(1, 0);
		case SOUTH:
			return new Vector2(0, -1);
		case WEST:
			return new Vector2(-1, 0);
		}
		return null;
	}

	

}