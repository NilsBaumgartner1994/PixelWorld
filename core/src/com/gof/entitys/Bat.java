package com.gof.entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.CameraControllerInterface;
import com.gof.physics.Position;
import com.gof.physics.Speed;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

import com.gof.items.AbstractItem;
import com.gof.items.Inventory;
import com.gof.items.Item;
import com.gof.items.Tool;

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

		this.nav.setPath(nextGoal.getGlobalPosition().addAndSet(0, MapTile.tileWidth / 2, 0, MapTile.tileHeight / 2));
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

	public List<Sprite> getSprite() {
		List<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(new Sprite(BatSpriteAnimations.getTexture(getMotionState(), this.world.time)));
		return sprites;
	}

}
