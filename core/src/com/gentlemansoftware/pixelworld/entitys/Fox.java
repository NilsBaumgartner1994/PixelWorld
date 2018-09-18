package com.gentlemansoftware.pixelworld.entitys;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.physics.Speed;
import com.gentlemansoftware.pixelworld.world.MapTile;
import com.gentlemansoftware.pixelworld.world.TileWorld;

import com.gentlemansoftware.pixelworld.items.AbstractItem;
import com.gentlemansoftware.pixelworld.items.Inventory;
import com.gentlemansoftware.pixelworld.items.Item;
import com.gentlemansoftware.pixelworld.items.Tool;

public class Fox extends Entity{

	public String name;
	private boolean sneaking;
	public Position direction;
	private Vector2 stickLeft;
	
	public Fox(TileWorld world){
		this(world, "Fred");
	}

	public Fox(TileWorld world,String name) {
		super(world,51721,MapTile.tileWidth/2, 50811,MapTile.tileHeight/2, EntityHostileType.PLAYER);
		this.name = name;
		this.resetInputVariables();

		sneaking = false;
	}

	public void sneak(boolean sneak) {
		sneaking = sneak;
		if (sneaking) {
			speed = Speed.sneak;
		}
	}

	public void run(boolean run) {
		// true : false
		if (!sneaking) {
			speed = run ? Speed.run : Speed.walk;
		}
	}

	public void setLeftStick(Vector2 dir) {
		this.stickLeft  = dir.cpy();
	}

	public void setRightStick(Vector2 dir) {

	}

	private void updateLeftStick() {
		if (this.stickLeft.len() != 0) {
			this.direction = Position.getPositionDirectionFromVector(this.stickLeft);
			Position nextBlock = getNextBlockInDirection();
			this.nav.setPath(nextBlock);
		}
	}
	
	private Position getNextBlockInDirection(){
		Position oneBlockDir = this.direction.cpy().scaleAndSet(MapTile.tileHeight);
		Position nextBlock = this.getPosition().cpy().addAndSet(oneBlockDir);
		nextBlock = this.world.getMapTileFromGlobalPos(nextBlock.getPosition().x,nextBlock.getPosition().y).getGlobalPosition().addAndSet(0, MapTile.tileWidth/2, 0, MapTile.tileHeight/2);
		return nextBlock;
	}

	public void resetInputVariables() {
		stickLeft = new Vector2();
		this.speed = Speed.walk;
	}

	public List<Sprite> getSprite() {
//		return PlayerSpriteCreator.getPlayerSprite(this);
	}

	@Override
	public void updateLogic() {
		updateLeftStick();
	}

}
