package com.gentlemansoftware.pixelworld.world;

import java.io.Serializable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.entitys.EasyDrawableInterface;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.entitys.EntityHostileType;
import com.gentlemansoftware.pixelworld.entitys.MotionState;
import com.gentlemansoftware.pixelworld.materials.MyMaterial;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.Position;

public class Block implements Serializable, EasyDrawableInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4737139312313773562L;

	public MyMaterial m;
	public transient MapTile tile;

	public Block() {
		super();
	}

	public Block(MapTile tile, MyMaterial m) {
		this.tile = tile;
		setMaterial(m);
	}

	private Sprite getMaterialSprite() {
		return new Sprite(m.getTexture());
	}

	public void setMaterial(MyMaterial m) {
		this.m = m;
	};

	public boolean isSolid() {
		return MyMaterial.isSolidMaterial(m);
	}

	public int getHeight() {
		return MyMaterial.getDefaultHeightByID(this.m.getID());
	}

	@Override
	public Sprite getSprite(Direction cameraDirection) {
		if (m == null)
			return new Sprite(MyMaterial.ERROR.getTexture());
		return getMaterialSprite();
	}

	@Override
	public Position getPosition() {
		return this.tile.getGlobalPosition().addAndSet(0, 0, 0, 0, 0, getHeight());
	}

}