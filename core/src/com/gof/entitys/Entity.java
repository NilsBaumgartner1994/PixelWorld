package com.gof.entitys;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.Main;
import com.gof.materials.Material;
import com.gof.physics.Body;
import com.gof.physics.Direction;
import com.gof.physics.Navigation;
import com.gof.physics.Position;
import com.gof.physics.Speed;
import com.gof.world.Chunk;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

public class Entity extends Body {

	private MotionState state;
	private Material material;
	private EntityType type;
	protected int speed;
	protected TileWorld world;

	Navigation nav;

	public Entity(TileWorld world, Position position, MotionState state, EntityType type) {
		super(position);
		this.world = world;
		this.nav = new Navigation(position);
		this.state = state;
		this.type = type;
		spawn();
	}

	public Entity(TileWorld world, Position position, EntityType type) {
		this(world, position, MotionState.STOP, type);
	}

	public Entity(TileWorld world, int x, int y, EntityType type) {
		this(world, new Position(x, y), MotionState.STOP, type);
	}

	public Entity(TileWorld world, int x, int xFraction, int y, int yFraction, EntityType type) {
		this(world, new Position(x, xFraction, y, yFraction), MotionState.STOP, type);
	}

	public void setDestiny(Position pos) {
		this.nav.setPath(pos);
	}

	public void setDestinyByOffset(Position pos) {
		setDestiny(this.getPosition().addAndSet(pos));
	}

	/**
	 * One Second are 60 steps
	 */
	@Override
	public void calcPhysicStep(int steps) {
		Position dir = Direction.getDirection(getPosition(), this.nav.getActualDestiny().cpy());
		dir.scaleAndSet(speed);
		Position clambed = Direction.clambIfDistanceToLong(getPosition(), this.nav.getActualDestiny(), dir);

		this.setVelocity(new Speed(clambed, 1, false));

		super.calcPhysicStep(steps);
	}

	public List<Sprite> getSprite() {
		return null;
	}

	public EntityType getEntityType() {
		return this.type;
	}

	public Material getMaterial() {
		return this.material;
	}

	public MotionState getMotionState() {
		return this.state;
	}

	public void spawn() {
		setPosition(this.getPosition());
		registerOnMapTile();
		registerOnChunk();
	}

	@Override
	public Entity setPosition(Position newpos) {
		super.setPosition(newpos);
		registerEntityOnMapTile(newpos);
		registerEntityOnChunk(newpos);
		return this;
	}

	public MapTile getMapTile() {
		return world.getMapTileFromGlobalPos(this.position.x, this.position.y);
	}
	
	public void registerOnMapTile(){
		getMapTile().registerEntity(this);
	}

	private void registerEntityOnMapTile(Position newpos) {
		MapTile newReffer = world.getMapTileFromGlobalPos(newpos.x, newpos.y);
		MapTile oldReffer = getMapTile();

		if (!(newReffer.getGlobalX() == oldReffer.getGlobalX() && newReffer.getGlobalY() == oldReffer.getGlobalY())) {
			oldReffer.unregisterEntity(this);
			newReffer.registerEntity(this);

		}
	}
	
	public void registerOnChunk(){
		getMapTile().chunk.registerEntity(this);
	}

	private void registerEntityOnChunk(Position newpos) {
		Chunk newChunkReffer = world.getChunkGlobalPos(newpos.x, newpos.y);
		Chunk oldChunkReffer = getMapTile().chunk;

		if (!(newChunkReffer.x == oldChunkReffer.x && newChunkReffer.y == oldChunkReffer.y)) {
			oldChunkReffer.unregisterEntity(this);
			newChunkReffer.registerEntity(this);
		}
	}

}
