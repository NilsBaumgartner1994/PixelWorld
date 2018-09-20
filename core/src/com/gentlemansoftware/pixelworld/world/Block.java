package com.gentlemansoftware.pixelworld.world;

import java.io.Serializable;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.entitys.EntityHostileType;
import com.gentlemansoftware.pixelworld.entitys.MotionState;
import com.gentlemansoftware.pixelworld.materials.MyMaterial;
import com.gentlemansoftware.pixelworld.physics.Direction;

public class Block extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4737139312313773562L;
	
	private boolean solid;
	public MyMaterial material;

	public Block(MapTile tile, MyMaterial m){
		super(tile.chunk.world,tile.getGlobalPosition(), MotionState.STOP,EntityHostileType.FRIENDLY);
		setMaterial(m);
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	private Sprite getMaterialSprite(){
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
		this.position.zFraction = height;
	}

	public boolean isSolid() {
		return this.solid;
	}

	@Override
	public Sprite getSprite(Direction cameraDirection){
		if(material==null) return new Sprite(MyMaterial.ERROR.getTexture());
		return getMaterialSprite();
	}

}