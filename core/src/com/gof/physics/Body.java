package com.gof.physics;

import com.gof.world.MapTile;

public class Body implements Comparable<Body> {
	
	Position position;
	Position velocity;
	Position acceleration;
	
	MapTile referrsTo;

	public Body(Position position, Position velocity, Position acceleration) {	
		setPosition(position);
		setVelocity(velocity);
		setAcceleration(acceleration);
	}
	
	public Body(){
		this(new Position(0, 0),new Position(0, 0),new Position(0, 0));
	}

	public Body(Position position) {
		this(position, new Position(0, 0), new Position(0, 0));
	}

	public Body(Position position, Position velocity) {
		this(position, velocity, new Position(0, 0));
	}

	public Position getPosition() {
		return position.cpy();
	}
	
	public Body setPosition(int x, int y){
		return setPosition(new Position(x,y));
	}
	
	public Body setPosition(Body b){
		return setPosition(b.position);
	}

	public Body setPosition(Position newpos) {
		registerBodyOnMapTile(newpos);
		
		this.position = newpos.cpy();
		
		return this;
	}
	
	private void registerBodyOnMapTile(Position newpos){
		MapTile newReffer = newpos.getMapTile();
		
		if(this.position==null){
			linkToMapTile(newReffer);
		}else{
			MapTile oldReffer = this.position.getMapTile();
			
			if(!(newReffer.getGlobalX()==oldReffer.getGlobalX() && newReffer.getGlobalY()==oldReffer.getGlobalY())){
				oldReffer.unregisterBody(this);
				linkToMapTile(newReffer);
			}			
		}	
	}
	
	private void linkToMapTile(MapTile newReffer){
		newReffer.registerBody(this);
	}
	
	public void calcPhysicStep(float deltaTime) {
		// float accelerationValue = getValueOfVector(acceleration);
//		float velocityValue = getValueOfVector(velocity);
//
//		// float accelerationAddition
//		//
//		// if(accelerationValue)
//
//		float velocityAdd = velocityValue * deltaTime;

		setPosition(getPosition().addAndSet(velocity));
	}

	public float getValueOfVector(Position vec) {
		return vec.lengthValue();
	}

	public Body setVelocity(Position velocity) {
		this.velocity = velocity.cpy();
		return this;
	}

	public Position getVelocity() {
		return this.velocity.cpy();
	}

	public Body addVelocity(Position velocityAdiition) {
		return setVelocity(getVelocity().addAndSet(velocityAdiition));
	}

	public Body setAcceleration(Position acceleration) {
		this.acceleration = acceleration.cpy();
		return this;
	}

	@Override
	public int compareTo(Body o) {
		return this.position.compareTo(o.position);
	}

}
