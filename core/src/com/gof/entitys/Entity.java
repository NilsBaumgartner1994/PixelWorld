package com.gof.entitys;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.Main;
import com.gof.physics.Body;
import com.gof.physics.Position;

public class Entity extends Body{
	
	private boolean isSelected;
	
	public Entity(int x, int y){
		this(new Position(x,y));
	}
	
	public Entity(Position position){
		super(position);
		
	}

	public List<Sprite> getSprite() {
		return null;
	}
	
	private void setSelected(boolean selected){
		this.isSelected = selected;
	}
	
	public boolean isSelected(){
		return this.isSelected;
	}
	
	public void select(){
		setSelected(true);
	}
	
	public void unselect(){
		setSelected(false);
	}
	
	
}
