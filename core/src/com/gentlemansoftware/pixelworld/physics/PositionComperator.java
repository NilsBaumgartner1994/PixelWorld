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
		if (this.direction == Direction.NORTH) {
			return getDirectionNorthCompare(pos1, pos2);
		}
		if (this.direction == Direction.EAST) {
			return getDirectionEastCompare(pos1, pos2);
		}
		if (this.direction == Direction.SOUTH) {
			return getDirectionSouthCompare(pos1, pos2);
		}
		if (this.direction == Direction.WEST) {
			return getDirectionWestCompare(pos1, pos2);
		}
		return -getDirectionNorthCompare(pos1, pos2);
	}
	
	private Position getGlobalPosIfMapTile(Position pos){
		if(pos instanceof MapTile){
			MapTile tile = (MapTile)pos;
			return tile.getGlobalPosition();
		}
		
		return pos;
	}

	public int getDirectionNorthCompare(Position pos1n, Position pos2n) {
		Position pos1 = getGlobalPosIfMapTile(pos1n);
		Position pos2 = getGlobalPosIfMapTile(pos2n);
		
		
		float me = heightCompareLength(pos1);
		float other = heightCompareLength(pos2);

		if (me > other) {
			return -1;
		}
		if (me < other) {
			return 1;
		}

		// both bodys same vertical height
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

	public int getDirectionWestCompare(Position pos1, Position pos2) {
		float me = heightCompareLength(pos1);
		float other = heightCompareLength(pos2);

		if (me > other) {
			return -1;
		}
		if (me < other) {
			return 1;
		}

		// both bodys same vertical height
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

	public int getDirectionSouthCompare(Position pos1, Position pos2) {
		return -getDirectionNorthCompare(pos1, pos2);
	}
	
	public int getDirectionEastCompare(Position pos1, Position pos2) {
		return -getDirectionWestCompare(pos1,pos2);
	}

	public float heightCompareLength(Position p) {
		if (direction == Direction.NORTH || direction == Direction.SOUTH) {
			return p.x + p.y + fractionLength(p);
		}
		if (direction == Direction.EAST || direction == Direction.WEST) {
			return - p.x + p.y + fractionLength(p);
		}
		return p.x + p.y + fractionLength(p);
	}

	private float fractionLength(Position p) {
		if (direction == Direction.NORTH || direction == Direction.SOUTH) {
			return p.fractionLengthX() + p.fractionLengthY();
		}
		if (direction == Direction.EAST || direction == Direction.WEST) {
			return  - p.fractionLengthX() + p.fractionLengthY();
		}
		return p.fractionLengthX() + p.fractionLengthY();
	}

}
