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
	
	public MyMaterial material;
	
	public Block(){
		super();
	}

	public Block(MapTile tile, MyMaterial m){
		super(tile.chunk.world,tile.getGlobalPosition(), MotionState.STOP,EntityHostileType.FRIENDLY,MyMaterial.isSolidMaterial(m));
		setMaterial(m);
	}

	private Sprite getMaterialSprite(){
		return new Sprite(material.getTexture());
	}
	
	public void setMaterial(MyMaterial m){
		this.material = m;
		this.setSolid(MyMaterial.isSolidMaterial(m));
		setHeight(MyMaterial.getDefaultHeightByID(this.material.getID()));
	}
	
	public void setHeight(int height){
		this.position.zFraction = height;
	}
	
	public int getHeight(){
		return this.position.zFraction;
	}

	@Override
	public Sprite getSprite(Direction cameraDirection){
		if(material==null) return new Sprite(MyMaterial.ERROR.getTexture());
		return getMaterialSprite();
	}

}