package com.gof.entitys;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.Main;
import com.gof.physics.Body;
import com.gof.physics.Position;

public class Entity extends Body{
	
	public Entity(int x, int y){
		this(new Position(x,y));
	}
	
	public Entity(Position position){
		super(position);
	}


	public List<Sprite> getSprite() {
		return null;
	}
	
	
}
