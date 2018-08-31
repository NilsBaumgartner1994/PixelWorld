package com.gof.entitys;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.Main;
import com.gof.materials.MyMaterial;
import com.gof.physics.Body;
import com.gof.physics.Direction;
import com.gof.physics.Navigation;
import com.gof.physics.Position;
import com.gof.physics.Speed;
import com.gof.world.Chunk;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

public class Entity extends Body implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5098608000401492288L;
	private MotionState state;
	private MyMaterial material;
	private EntityHostileType type;
	protected int speed;
	protected TileWorld world;
	private int id;

	Navigation nav;

	public Entity(TileWorld world, Position position, MotionState state, EntityHostileType type) {
		super(position);
		this.world = world;
		this.nav = new Navigation(position);
		this.state = state;
		this.type = type;
		spawn();
	}

	public Entity(TileWorld world, Position position, EntityHostileType type) {
		this(world, position, MotionState.STOP, type);
	}

	public Entity(TileWorld world, int x, int y, EntityHostileType type) {
		this(world, new Position(x, y), type);
	}

	public Entity(TileWorld world, int x, int xFraction, int y, int yFraction, EntityHostileType type) {
		this(world, new Position(x, xFraction, y, yFraction), type);
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
		if (!this.nav.hasFinished()) {
			Position actualDestiny = this.nav.getActualDestiny().cpy();
			Position dir = Position.getDirection(getPosition(), actualDestiny);
			dir.scaleAndSet(speed);

			Position clambed = Position.clambIfDistanceToLong(getPosition(), actualDestiny, dir);
			this.setVelocity(new Speed(clambed, 1, false));

			super.calcPhysicStep(steps);

			if (getPosition().equals(actualDestiny)) {
				this.nav.arrivedAtDestiny();
			}
		}
	}

	public void updateLogic() {
		// Set Goals and Other calculations about the AI
	}

	public List<Sprite> getSprite() {
		return null;
	}

	public EntityHostileType getEntityHostileType() {
		return this.type;
	}

	public MyMaterial getMaterial() {
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
		registerEntityOnMapTile(newpos);
		registerEntityOnChunk(newpos);
		super.setPosition(newpos);
		return this;
	}

	public MapTile getMapTile() {
		return world.getMapTileFromGlobalPos(this.position.x, this.position.y);
	}

	private void registerOnMapTile() {
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

	private void registerOnChunk() {
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
