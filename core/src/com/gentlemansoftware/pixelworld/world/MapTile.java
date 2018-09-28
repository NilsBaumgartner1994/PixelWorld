package com.gentlemansoftware.pixelworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.materials.MyMaterial;
import com.gentlemansoftware.pixelworld.physics.Body;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.Position;

public class MapTile extends Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 216668977777721960L;
	public final static transient int tileWidth = 128;
	public final static transient int tileHeight = 64;
	
	private boolean shaddow;

	public transient Chunk chunk;
	public Block block;
	
	public List<Entity> entitys;
	private boolean solid;
	
	public MapTile(){
		
	}
	
	public void setTransients(Chunk chunk){
		this.chunk = chunk;
		block.setTransient(this.chunk.world);
	}

	public MapTile(Chunk c, int x, int y) {
		super(x, y);
		entitys = new ArrayList<Entity>();
		this.chunk = c;
		setSolid(solid);
	}
	
	public void setBlock(Block b){
		this.block = b;
	}
	
	public void checkSolid(){
		this.setSolid(false);
		
		for(Entity e : entitys){
			if(e.isSolid()){
				this.setSolid(true);
				return;
			}
		}
	}
	
	public void registerEntity(Entity body){
		if(!entitys.contains(body)){
			entitys.add(body);
			checkSolid();
		}
	}
	
	public void unregisterEntity(Body body){
		entitys.remove(body);
		checkSolid();
	}

	private MapTile getOffset(int xi, int yi) {
		xi = (int) (this.getPosition().x + xi);
		yi = (int) (this.getPosition().y + yi);

		if (xi < 0 || xi > Chunk.CHUNKSIZE || yi < 0 || yi > Chunk.CHUNKSIZE) {
			return null;
		}
		return chunk.getMapTileFromLocalPos(xi, yi);
	}
	
	public Position getGlobalPosition(){
		return new Position(getGlobalX(),getGlobalY());
	}

	public int getGlobalX() {
		if(this.getPosition()==null || chunk==null) return 0;
		return (int) (chunk.x * Chunk.CHUNKSIZE + this.getPosition().x);
	}

	public int getGlobalY() {
		if(this.getPosition()==null || chunk==null) return 0;
		return (int) (chunk.y * Chunk.CHUNKSIZE + this.getPosition().y);
	}
	
	public boolean isSolid() {
		return this.solid;
	}
	
	private void setSolid(boolean bool){
		this.solid = bool;
	}
	
	private void setShaddow(boolean selected){
		this.shaddow = selected;
	}
	
	public boolean isInShaddow(){
		return this.shaddow;
	}
	
	public void select(){
		setShaddow(true);
	}
	
	public void unselect(){
		setShaddow(false);
	}
	
	public String toString(){
		return "Chunk: "+this.chunk.x+":"+this.chunk.y+" LocalPos: "+this.x+":"+this.y+" GlobalPos: "+this.getGlobalX()+":"+this.getGlobalY();
	}

}