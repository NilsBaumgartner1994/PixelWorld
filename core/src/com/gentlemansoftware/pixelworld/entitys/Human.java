package com.gentlemansoftware.pixelworld.entitys;

import java.util.ArrayList;
import java.util.List;

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

public class Human extends Entity {

	public String name;

	/**
	 * InputVariables
	 */
	private boolean sneaking;

	public Inventory inventory;

	public boolean use = false;
	public final long USECOOLDOWN = 1000 / 10L;
	public long lastUse = System.currentTimeMillis();
	public Position usePosition;

	public Human(TileWorld world, Position startPos, String name) {
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
					// this.getMapTile().setMaterial(item.getMaterial());
				}
			}
			if (activItem instanceof Tool) {
				Tool tool = (Tool) activItem;
				// getMapTile().removeNature();
			}

		}
	}

	public void updateLeftStick(Vector2 stickLeft) {
		if (stickLeft.len() != 0) {
			Position dir = Position.getPositionDirectionFromVector(stickLeft);
			MapTile nextBlock = getNextBlockInDirection(dir);
			if (!nextBlock.isSolid()) {
				Position nextBlockMiddle = nextBlock.getGlobalPosition().addAndSet(0, 0, 0, 0, 1, 0);
				this.nav.setSecondDestiny(nextBlockMiddle);
			}
		}
	}

	public MapTile getNextBlockInDirection(Position direction) {
		Position oneBlockDir = direction.cpy().scaleAndSet(MapTile.tileWidth, MapTile.tileHeight);
		Position nextBlock = this.getPosition().cpy().addAndSet(oneBlockDir);
		return this.world.getMapTileFromGlobalPos(nextBlock.getPosition().x, nextBlock.getPosition().y);
	}

	public void resetInputVariables() {
		this.speed = Speed.walk;
	}

	@Override
	public Sprite getSprite(Direction camdir) {
		return new Sprite(ResourceLoader.getInstance().getEntity("layerTest", "layerTest"));

		// return new Sprite(FoxSpriteAnimations.getTexture(getMotionState(),
		// this.world.time));
	}

	@Override
	public void updateLogic() {
		updateUse();
	}

}
