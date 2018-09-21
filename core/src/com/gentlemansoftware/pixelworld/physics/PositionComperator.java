package com.gentlemansoftware.pixelworld.physics;

import java.util.Comparator;

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
		float xLength = p.lengthX();
		float yLength = p.lengthY();

		switch (direction) {
		case NORTH:
			return zHeight + xLength + yLength;
		case SOUTH:
			return zHeight - xLength - yLength;
		case WEST:
			return zHeight - xLength + yLength;
		case EAST:
			return zHeight + xLength - yLength;
		default:
			return 0;
		}
	}

}
