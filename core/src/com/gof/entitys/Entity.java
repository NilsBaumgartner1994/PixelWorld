package com.redagent.entitys;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.redagent.game.Main;
import com.redagent.physics.Body;
import com.redagent.physics.Position;

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
