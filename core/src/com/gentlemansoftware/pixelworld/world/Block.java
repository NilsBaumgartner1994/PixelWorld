package com.gentlemansoftware.pixelworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Model;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.entitys.EntityHostileType;
import com.gentlemansoftware.pixelworld.entitys.MotionState;
import com.gentlemansoftware.pixelworld.materials.MyMaterial;
import com.gentlemansoftware.pixelworld.nature.Nature;
import com.gentlemansoftware.pixelworld.physics.Body;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.Position;

public class Block extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4737139312313773562L;
	
	private boolean solid;
	public MyMaterial material;
	private int height;

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
		this.height = height;
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