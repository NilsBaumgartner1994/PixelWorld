package com.gentlemansoftware.pixelworld.physics;

import java.util.Comparator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gentlemansoftware.pixelworld.game.SaveAndLoadable;
import com.gentlemansoftware.pixelworld.world.MapTile;

public class PositionComperator implements Comparator<Position> {

	Direction direction;

	public PositionComperator(Direction direction) {
		this.direction = direction;
	}

	@Override
	public int compare(Position pos1, Position pos2) {
		int omni = getOmniDirectionalCompare(pos1, pos2);
		if (omni != 0) {
			return omni;
		}

		if (this.direction == Direction.NORTH || this.direction == Direction.SOUTH) {
			return getDirectionNorthSouthCompare(pos1, pos2);
		}
		if (this.direction == Direction.WEST || this.direction == Direction.EAST) {
			return getDirectionEastWestCompare(pos1, pos2);
		}

		return 0;
	}

	public int getOmniDirectionalCompare(Position pos1, Position pos2) {
		float me = heightCompareLength(pos1);
		float other = heightCompareLength(pos2);

		if (me > other) {
			return -1;
		}
		if (me < other) {
			return 1;
		}

		// both bodys same vertical height

		// no that are realy object on same height
		if (pos1.zFraction > pos2.zFraction) {
			return -1;
		}
		if (pos1.zFraction < pos2.zFraction) {
			return 1;
		}

		return 0;
	}

	public int getDirectionNorthSouthCompare(Position pos1, Position pos2) {
		if (pos1.x < pos2.x) {
			return 1;
		}
		if (pos1.x > pos2.x) {
			return -1;
		}

		if (pos1.xFraction < pos2.xFraction) {
			return 1;
		}
		if (pos1.xFraction > pos2.xFraction) {
			return -1;
		}

		return 0;
	}

	public int getDirectionEastWestCompare(Position pos1, Position pos2) {
		if (pos1.y < pos2.y) {
			return 1;
		}
		if (pos1.y > pos2.y) {
			return -1;
		}

		if (pos1.yFraction < pos2.yFraction) {
			return 1;
		}
		if (pos1.yFraction > pos2.yFraction) {
			return -1;
		}

		return 0;
	}

	public float heightCompareLength(Position p) {
		int zHeight = -p.z - (p.zFraction > 0 ? 1 : 0);
		float x = p.fractionLengthX();
		float y = p.fractionLengthY();

		switch (direction) {
		case NORTH:
			return zHeight + (p.x + p.y + x + y);
		case SOUTH:
			return zHeight + (-p.x - p.y - x - y);
		case WEST:
			return zHeight + (-p.x + p.y - x + y);
		case EAST:
			return zHeight + (p.x - p.y + x - y);
		default:
			return 0;
		}
	}

	private float fractionLength(Position p) {
		float x = p.fractionLengthX();
		float y = p.fractionLengthY();
		boolean northSouth = (direction == Direction.NORTH || direction == Direction.SOUTH);
		return northSouth ? x + y : -x + y;

		// if (direction == Direction.NORTH || direction == Direction.SOUTH) {
		// return p.fractionLengthX() + p.fractionLengthY();
		// }
		// if (direction == Direction.EAST || direction == Direction.WEST) {
		// return -p.fractionLengthX() + p.fractionLengthY();
		// }
		// return p.fractionLengthX() + p.fractionLengthY();
	}

}
