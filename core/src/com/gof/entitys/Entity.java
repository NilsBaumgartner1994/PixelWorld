package com.gof.entitys;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.Main;
import com.gof.materials.Material;
import com.gof.physics.Body;
import com.gof.physics.Position;

public class Entity extends Body{
	
	public MotionState state;
	public Material material;
	
	public Entity(Position position, MotionState state){
		super(position);
		this.state = state;
	}
	
	public Entity(Position position){
		this(position,MotionState.STOP);
	}

	public Entity(int x, int y, MotionState state){
		this(new Position(x,y),state);
	}	
	
	public Entity(int x, int y){
		this(new Position(x,y),MotionState.STOP);
	}

	public List<Sprite> getSprite() {
		return null;
	}
	
}
