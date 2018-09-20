package com.gentlemansoftware.pixelworld.physics;

import java.util.Comparator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.game.SaveAndLoadable;
import com.gentlemansoftware.pixelworld.world.MapTile;

public class EntityComperator implements Comparator<Entity> {

	Direction direction;
	PositionComperator poscomp;

	public EntityComperator(Direction direction) {
		this.direction = direction;
		poscomp = new PositionComperator(this.direction);
	}

	@Override
	public int compare(Entity o1, Entity o2) {
		return poscomp.compare(o1.getPosition(), o2.getPosition());
	}

}
