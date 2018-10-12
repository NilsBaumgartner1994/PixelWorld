package com.gentlemansoftware.pixelworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.physics.Body;
import com.gentlemansoftware.pixelworld.physics.Position;

public class MapTile extends Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 216668977777721960L;
	public final static transient int tileWidth = 128;
	public final static transient int tileHeight = 64;

	public transient Chunk chunk;
	public Block b;

	public List<Entity> e;
	private transient boolean solid;

	public MapTile() {
		
	}

	public void setTransients(Chunk chunk) {
		this.chunk = chunk;
		this.b.tile = this;
	}

	public MapTile(Chunk c, int x, int y) {
		super(x, y);
		e = new ArrayList<Entity>();
		this.chunk = c;
		setSolid(solid);
	}

	public void setBlock(Block b) {
		this.b = b;
	}

	public void checkSolid() {
		this.setSolid(false);

		if (b != null) {
			setSolid(b.isSolid());
		}

		for (Entity e : e) {
			if (e.isSolid()) {
				this.setSolid(true);
				return;
			}
		}
	}

	public void registerEntity(Entity body) {
		if (!e.contains(body)) {
			e.add(body);
			checkSolid();
		}
	}

	public void unregisterEntity(Body body) {
		e.remove(body);
		checkSolid();
	}

	public Position getGlobalPosition() {
		return new Position(getGlobalX(), getGlobalY());
	}

	public int getGlobalX() {
		if (this.getPosition() == null || chunk == null)
			return 0;
		return (int) (chunk.x * Chunk.CHUNKSIZE + this.getPosition().x);
	}

	public int getGlobalY() {
		if (this.getPosition() == null || chunk == null)
			return 0;
		return (int) (chunk.y * Chunk.CHUNKSIZE + this.getPosition().y);
	}

	public boolean isSolid() {
		return this.solid;
	}

	private void setSolid(boolean bool) {
		this.solid = bool;
	}

	public String toString() {
		return "Chunk: " + this.chunk.x + ":" + this.chunk.y + " LocalPos: " + this.x + ":" + this.y + " GlobalPos: "
				+ this.getGlobalX() + ":" + this.getGlobalY();
	}

}