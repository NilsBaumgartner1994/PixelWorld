package com.gof.entitys;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.CameraController;
import com.gof.game.Main;
import com.gof.helper.VectorHelper;
import com.gof.materials.Debug;
import com.gof.materials.Grass;
import com.gof.materials.Material;
import com.gof.materials.MaterialError;
import com.gof.materials.Stone;
import com.gof.materials.Water;
import com.gof.menu.MenuHandler;
import com.gof.nature.Nature;
import com.gof.nature.TallGrass;
import com.gof.nature.Tree;
import com.gof.physics.Direction;
import com.gof.physics.Position;
import com.gof.physics.Speed;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

import com.gof.items.AbstractItem;
import com.gof.items.Inventory;
import com.gof.items.Item;
import com.gof.items.Tool;

public class Human extends Entity{

	public String name;

	/**
	 * InputVariables
	 */
	private Vector2 stickLeft;
	public boolean stickLeftDown;

	private Vector2 stickRight;
	public boolean stickRightDown;

	public CameraController cameraController;

	private boolean sneaking;

	public Position direction;

	public Inventory inventory;
	
	public boolean use = false;
	public final long USECOOLDOWN = 1000 / 10L;
	public long lastUse = System.currentTimeMillis();
	public Position usePosition;
	
	public Human(TileWorld world){
		this(world, "Bob");
	}

	public Human(TileWorld world,String name) {
		super(world,51721,MapTile.tileWidth/2, 50811,MapTile.tileHeight/2, EntityType.PLAYER);
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
		this.inventory.addItem(new Item(new Tree()));
		this.inventory.addItem(new Item(new TallGrass()));
		this.inventory.addItem(new Tool());
		this.inventory.addItem(new Item(new Grass()));
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
					this.getMapTile().setNature(item.getNature());
				}
				else{
					this.getMapTile().setMaterial(item.getMaterial());
				}
			}
			if (activItem instanceof Tool) {
				Tool tool = (Tool) activItem;
				getMapTile().removeNature();
			}

		}
	}

	private void updateLeftStick() {
		if (this.stickLeft.len() != 0) {
			this.direction = Position.getPositionDirectionFromVector(this.stickLeft);
			Position nextBlock = getNextBlockInDirection();
			this.nav.setPath(nextBlock);
		}
	}
	
	public Position getNextBlockInDirection(){
		Position oneBlockDir = this.direction.cpy().scaleAndSet(MapTile.tileHeight);
		Position nextBlock = this.getPosition().cpy().addAndSet(oneBlockDir);
		nextBlock = this.world.getMapTileFromGlobalPos(nextBlock.getPosition().x,nextBlock.getPosition().y).getGlobalPosition().addAndSet(0, MapTile.tileWidth/2, 0, MapTile.tileHeight/2);
		return nextBlock;
	}

	public void resetInputVariables() {
		stickLeft = new Vector2();
		stickRight = new Vector2();
		this.speed = Speed.walk;
	}

	public List<Sprite> getSprite() {
		return PlayerSpriteCreator.getPlayerSprite(this);
	}

	@Override
	public void updateLogic() {
		updateLeftStick();
		updateUse();
	}

}
