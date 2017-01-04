package com.gof.physics;

public class Body implements Comparable<Body> {

	Position position;
	Position velocity;
	Position acceleration;
	
	com.badlogic.gdx.physics.box2d.Body body;

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

	public Body setPosition(Position newpos) {
		this.position = newpos.cpy();
		return this;
	}
	
	public Body setPosition(int x, int y){
		return setPosition(new Position(x,y));
	}
	
	public Body setPosition(Body b){
		return setPosition(b.position);
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

		setPosition(getPosition().add(velocity));
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
		return setVelocity(getVelocity().add(velocityAdiition));
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
