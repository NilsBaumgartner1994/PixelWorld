package com.gof.entitys;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.Main;
import com.gof.materials.Material;
import com.gof.physics.Body;
import com.gof.physics.Direction;
import com.gof.physics.Navigation;
import com.gof.physics.Position;
import com.gof.physics.Speed;

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
	
	public Entity(int x,int xFraction, int y, int yFraction, EntityType type) {
		this(new Position(x,xFraction,y,yFraction),MotionState.STOP,type);
	}

	public void setDestiny(Position pos){
		this.nav.setPath(pos);
	}
	
	public void setDestinyByOffset(Position pos){
		setDestiny(this.getPosition().addAndSet(pos));
	}
	
	/**
	 * One Second are 60 steps
	 */
	@Override
	public void calcPhysicStep(int steps) {		
		Position dir = Direction.getDirection(getPosition(), this.nav.getActualDestiny().cpy());
		dir.scaleAndSet(speed);
		Position clambed = Direction.clambIfDistanceToLong(getPosition(), this.nav.getActualDestiny(), dir);
		
		this.setVelocity(new Speed(clambed,1,false));
		
		super.calcPhysicStep(steps);
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
