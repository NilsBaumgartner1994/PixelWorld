package com.gentlemansoftware.pixelworld.entitys;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.physics.Body;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.physics.Speed;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.searchstrategies.Navigation;
import com.gentlemansoftware.pixelworld.world.Chunk;
import com.gentlemansoftware.pixelworld.world.MapTile;
import com.gentlemansoftware.pixelworld.world.TileWorld;

public class Entity extends Body implements Serializable, EasyDrawableInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5098608000401492288L;
	private MotionState state;
	private boolean solid;
	private EntityHostileType type;
	protected int speed;
	public transient TileWorld world;
	private String uuid;

	Navigation nav;

	public Entity() {

	}

	public void setTransient(TileWorld world) {
		this.world = world;
	}

	public Entity(TileWorld world, Position position, MotionState state, EntityHostileType type, boolean solid) {
		super(position);
		this.world = world;
		this.nav = new Navigation();
		this.nav.addDestiny(position);
		this.state = state;
		this.type = type;
		this.setSolid(solid);
	}

	public Entity(TileWorld world, Position position, EntityHostileType type, boolean solid) {
		this(world, position, MotionState.STOP, type, solid);
	}

	public Entity(TileWorld world, Position position, EntityHostileType type) {
		this(world, position, MotionState.STOP, type, true);
	}

	public Entity(TileWorld world, int x, int y, EntityHostileType type) {
		this(world, new Position(x, y), type);
	}

	public Entity(TileWorld world, int x, int xFraction, int y, int yFraction, EntityHostileType type) {
		this(world, new Position(x, xFraction, y, yFraction), type);
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

	public String getUUID() {
		return this.uuid;
	}

	public void setDestiny(Position pos) {
		this.nav.setPath(pos);
	}

	public void setDestinyByOffset(Position pos) {
		setDestiny(this.getPosition().addAndSet(pos));
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isSolid() {
		return this.solid;
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

	public EntityHostileType getEntityHostileType() {
		return this.type;
	}

	public MotionState getMotionState() {
		return this.state;
	}

	public void spawn() {
		setPosition(this.getPosition());
		registerOnMapTile();
		registerOnChunk();
	}

	// public void setPosition(Position newpos) {
	//
	// }

	@Override
	public Entity setPosition(Position newpos) {
		registerEntityOnMapTile(newpos);
		registerEntityOnChunk(newpos);
		super.setPosition(newpos);
		return this;
	}

	public void playSoundForUser(Position playerPos, User user) {

	}

	public MapTile getMapTile() {
		return world.getMapTileFromGlobalPos(this.getPosition().x, this.getPosition().y);
	}

	private void registerOnMapTile() {
		getMapTile().registerEntity(this);
	}

	private void registerEntityOnMapTile(Position newpos) {
		MapTile newReffer = world.getMapTileFromGlobalPos(newpos.x, newpos.y);
		MapTile oldReffer = getMapTile();

		if (newReffer != oldReffer) { // what if new Maptile is copied and
										// placed?
			oldReffer.unregisterEntity(this);
			newReffer.registerEntity(this);
		}
	}

	private void registerOnChunk() {
		getMapTile().chunk.registerEntity(this);
	}

	public Chunk getChunk() {
		Position p = this.getPosition();
		return world.getChunkGlobalPos(p.x, p.y);
	}

	public Position getPositionInChunk() {
		Chunk c = getChunk();
		Position p = this.getPosition();
		Position posInChunk = new Position(p.x - c.getGloabalPosX(), p.y - c.getGloabalPosY());
		return posInChunk;
	}

	private void registerEntityOnChunk(Position newpos) {
		Chunk newChunkReffer = world.getChunkGlobalPos(newpos.x, newpos.y);
		Chunk oldChunkReffer = getMapTile().chunk;

		if (newChunkReffer != oldChunkReffer) { // what if new chunk is placed
			oldChunkReffer.unregisterEntity(this);
			newChunkReffer.registerEntity(this);
		}
	}

	private void unregisterChunkReffer() {
		Chunk oldChunkReffer = getMapTile().chunk;
		oldChunkReffer.unregisterEntity(this);
	}

	private void unregisterMapTileReffer() {
		MapTile oldReffer = getMapTile();
		oldReffer.unregisterEntity(this);
	}
	
	private void unregisterEntityHandler(){
		world.entityhandler.unregisterEntity(this);
	}

	public void destroy() {
		unregisterChunkReffer();
		unregisterMapTileReffer();
		unregisterEntityHandler();
	}

	@Override
	public Sprite getSprite(Direction cameraDirection) {
		return null;
	}

}
