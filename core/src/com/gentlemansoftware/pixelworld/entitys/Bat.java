package com.gentlemansoftware.pixelworld.entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.physics.Speed;
import com.gentlemansoftware.pixelworld.world.MapTile;
import com.gentlemansoftware.pixelworld.world.TileWorld;

import com.gentlemansoftware.pixelworld.items.AbstractItem;
import com.gentlemansoftware.pixelworld.items.Inventory;
import com.gentlemansoftware.pixelworld.items.Item;
import com.gentlemansoftware.pixelworld.items.Tool;

public class Bat extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6290502169526685385L;

	public Bat(TileWorld world, Position globalPos) {
		super(world, globalPos, EntityHostileType.ANIMAL);
		this.resetInputVariables();
		setNextRandomGoal();
	}

	@Override
	public void updateLogic() {
		if (this.nav.hasFinished()) { // find next random Position to move
			setNextRandomGoal();
		}
	}

	private void setNextRandomGoal() {
		MapTile nextGoal = getNextGoal();
		while (nextGoal.isSolid()) {
			nextGoal = getNextGoal();
		}

		this.nav.setPath(nextGoal.getGlobalPosition().addAndSet(0, 0, 0, 0, 1, 0));
	}

	private MapTile getNextGoal() {
		MapTile tile = this.getMapTile();
		Random r = new Random();
		int min = 3;
		int max = 10;
		int distance = min + r.nextInt(max + 1);

		boolean inXDir = r.nextBoolean();
		int xDir = inXDir ? 1 : 0;
		int yDir = inXDir ? 0 : 1;
		int positive = r.nextBoolean() ? 1 : -1;

		int xAdd = xDir * positive * distance;
		int yAdd = yDir * positive * distance;

		return this.world.getMapTileFromGlobalPos(tile.getGlobalX() + xAdd, tile.getGlobalY() + yAdd);
	}

	public void resetInputVariables() {
		this.speed = Speed.sneak;
	}

	@Override
	public Sprite getSprite(Direction camdir) {
		return new Sprite(BatSpriteAnimations.getTexture(getMotionState(), this.world.time));
	}

}
