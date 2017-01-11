package com.gof.entitys;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.Main;
import com.gof.materials.Material;
import com.gof.physics.Body;
import com.gof.physics.Navigation;
import com.gof.physics.Position;

public class Entity extends Body{
	
	private MotionState state;
	private Material material;
	private EntityType type;
	protected int speed;
	
	Navigation nav;
	
	public Entity(Position position, MotionState state, EntityType type){
		super(position);
		this.nav = new Navigation(position);
		this.state = state;
		this.type = type;
	}
	
	public Entity(Position position, EntityType type){
		this(position,MotionState.STOP,type);
	}
	
	public Entity(int x, int y, EntityType type) {
		this(new Position(x,y),MotionState.STOP,type);
	}

	public void setDestiny(Position pos){
		this.nav.setPath(pos);
	}
	
	public void setDestinyByOffset(Position pos){
		setDestiny(this.getPosition().addAndSet(pos));
	}
	
	@Override
	public void calcPhysicStep(float deltaTime) {		
		Position vel = this.nav.getActualDestiny().cpy().addAndSet(this.getPosition().scaleAndSet(-1));
		this.setVelocity(vel);
		super.calcPhysicStep(deltaTime);
	}

	public List<Sprite> getSprite() {
		return null;
	}
	
	public EntityType getEntityType(){
		return this.type;
	}
	
	public Material getMaterial(){
		return this.material;
	}
	
	public MotionState getMotionState(){
		return this.state;
	}
	
}
