package com.gof.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Model;
import com.gof.entitys.Entity;
import com.gof.materials.MyMaterial;
import com.gof.nature.Nature;
import com.gof.physics.Body;
import com.gof.physics.Direction;
import com.gof.physics.Position;

public class MapTile extends Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 216668977777721960L;
	public final static transient int tileWidth = 128;
	public final static transient int tileHeight = 64;

	public int height;
	public short direction;
	private boolean solid;

	public MyMaterial material;
	public Nature nature;
	
	private boolean shaddow;

	public transient Chunk chunk;
	
	public List<Entity> entitys;

	public MapTile(Chunk c, int x, int y, boolean solid, MyMaterial m) {
		super(x, y);
		entitys = new ArrayList<Entity>();
		this.chunk = c;
		setSolid(solid);
		setMaterial(m);
	}
	
	public void registerEntity(Entity body){
		if(!entitys.contains(body)){
			entitys.add(body);
		}
	}
	
	public void unregisterEntity(Body body){
		entitys.remove(body);
	}

	private MapTile getOffset(int xi, int yi) {
		xi = (int) (this.getPosition().x + xi);
		yi = (int) (this.getPosition().y + yi);

		if (xi < 0 || xi > Chunk.CHUNKSIZE || yi < 0 || yi > Chunk.CHUNKSIZE) {
			return null;
		}
		return chunk.getMapTileFromLocalPos(xi, yi);
	}

	public int getRotation() {
		return direction * 90;
	}
	
	public Position getGlobalPosition(){
		return new Position(getGlobalX(),getGlobalY());
	}

	public int getGlobalX() {
		return (int) (chunk.x * Chunk.CHUNKSIZE + this.getPosition().x);
	}

	public int getGlobalY() {
		return (int) (chunk.y * Chunk.CHUNKSIZE + this.getPosition().y);
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public Sprite getNatureTexture(){
		if(nature==null) return null;
		return new Sprite(nature.getTexture());
	}
	
	public void setNature(Nature n){
		this.nature = n;
		if(n.equals(Nature.TREE)){
			setSolid(true);
		}
	}
	
	public Sprite getMaterialSprite(){
		return new Sprite(material.getTexture());
	}
	
	public void setMaterial(MyMaterial m){
		this.material = m;
		if(m.equals(MyMaterial.WATER)){
			setSolid(true);
		}
		setHeight(MyMaterial.getDefaultHeightByID(this.material.getID()));
	}
	
	public void setHeight(int height){
		this.height = height;
	}

	public boolean isSolid() {
		return this.solid;
	}

	public void setDirection(short dir) {
		this.direction = dir;
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

	public List<MapTile> getNeumann() {
		List<MapTile> back = new ArrayList<MapTile>();
		back.add(getOffset(1, 0));
		back.add(getOffset(-1, 0));
		back.add(getOffset(0, 1));
		back.add(getOffset(0, -1));
		return back;
	}

	public List<MapTile> getMoore() {
		List<MapTile> back = getNeumann();
		back.add(getOffset(-1, -1));
		back.add(getOffset(-1, 1));
		back.add(getOffset(1, -1));
		back.add(getOffset(1, 1));
		return back;
	}
	
	public String toString(){
		return "Chunk: "+this.chunk.x+":"+this.chunk.y+" LocalPos: "+this.x+":"+this.y+" GlobalPos: "+this.getGlobalX()+":"+this.getGlobalY();
	}

}