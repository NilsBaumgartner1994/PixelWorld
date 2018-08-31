package com.gof.entitys;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.CameraControllerInterface;
import com.gof.physics.Direction;
import com.gof.physics.Position;
import com.gof.physics.Speed;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

import com.gof.items.AbstractItem;
import com.gof.items.Inventory;
import com.gof.items.Item;
import com.gof.items.Tool;

public class Human extends Entity {

	public String name;

	/**
	 * InputVariables
	 */
	private Vector2 stickLeft;
	public boolean stickLeftDown;

	private Vector2 stickRight;
	public boolean stickRightDown;

	public CameraControllerInterface cameraController;

	private boolean sneaking;

	public Position direction;

	public Inventory inventory;

	public boolean use = false;
	public final long USECOOLDOWN = 1000 / 10L;
	public long lastUse = System.currentTimeMillis();
	public Position usePosition;

	public Human(TileWorld world, Position startPos,String name) {
		super(world, startPos, EntityHostileType.PLAYER);
		this.name = name;

		sneaking = false;
		initInventory();
		resetInputVariables();
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

	public void initInventory() {
		this.inventory = new Inventory();
		// this.inventory.addItem(new Item(new Tree()));
		// this.inventory.addItem(new Item(new TallGrass()));
		this.inventory.addItem(new Tool());
		// this.inventory.addItem(new Item(new Grass()));
	}

	public void setLeftStick(Vector2 dir) {
		this.stickLeft = dir.cpy();
	}

	public void setRightStick(Vector2 dir) {

	}

	public void use(Position pos) {
		if (this.lastUse + USECOOLDOWN < System.currentTimeMillis()) {
			this.use = true;
			this.lastUse = System.currentTimeMillis();
			this.usePosition = pos.cpy();
			// Main.log(getClass(), "Use Called");
		}
	}

	private void updateUse() {
		if (this.use) {
			this.use = false;
			this.lastUse = System.currentTimeMillis();

			AbstractItem activItem = this.inventory.getActivItem();
			if (activItem instanceof Item) {
				Item item = (Item) activItem;
				if (item.isNature()) {
					// this.getMapTile().setNature(item.getNature());
				} else {
					this.getMapTile().setMaterial(item.getMaterial());
				}
			}
			if (activItem instanceof Tool) {
				Tool tool = (Tool) activItem;
				// getMapTile().removeNature();
			}

		}
	}

	private void updateLeftStick() {
		if (this.stickLeft.len() != 0) {
			this.direction = Position.getPositionDirectionFromVector(this.stickLeft);
			MapTile nextBlock = getNextBlockInDirection();
			if (!nextBlock.isSolid()) {
				Position nextBlockMiddle = nextBlock.getGlobalPosition().addAndSet(0, MapTile.tileWidth / 2, 0,
						MapTile.tileHeight / 2);
				this.nav.setPath(nextBlockMiddle);
			}
		}
	}

	public MapTile getNextBlockInDirection() {
		Position oneBlockDir = this.direction.cpy().scaleAndSet(MapTile.tileHeight);
		Position nextBlock = this.getPosition().cpy().addAndSet(oneBlockDir);
		return this.world.getMapTileFromGlobalPos(nextBlock.getPosition().x, nextBlock.getPosition().y);
	}

	public void resetInputVariables() {
		stickLeft = new Vector2();
		stickRight = new Vector2();
		this.speed = Speed.walk;
	}

	public List<Sprite> getSprite() {
		// return PlayerSpriteCreator.getPlayerSprite(this);
		List<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(new Sprite(FoxSpriteAnimations.getTexture(getMotionState(), this.world.time)));
		return sprites;
	}

	@Override
	public void updateLogic() {
		updateLeftStick();
		updateUse();
	}

}
