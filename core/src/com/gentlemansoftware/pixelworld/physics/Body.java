package com.gentlemansoftware.pixelworld.physics;

public class Body {

	protected Position position;
	private Speed velocity;
	protected Position acceleration;

	// MapTile referrsTo;

	public Body(Position position, Speed velocity, Position acceleration) {
		this.position = position.cpy();
		setVelocity(velocity);
		setAcceleration(acceleration);
	}

	public Body() {
		this(new Position(), new Speed(), new Position());
	}

	public Body(Position position) {
		this(position, new Speed(), new Position());
	}

	public Body(Position position, Speed velocity) {
		this(position, velocity, new Position());
	}

	public Position getPosition() {
		return position.cpy();
	}
	
	public Body setPositionForce(Body body) {
		return setPosition(body.getPosition());
	}

	public Body setPositionForce(int x, int y) {
		return setPosition(new Position(x, y));
	}
	
	public Body setPositionForce(Position pos) {
		return setPosition(pos);
	}

	protected Body setPosition(Position newpos) {
		this.position = newpos.cpy();

		return this;
	}

	/**
	 * One Second are 60 steps
	 * 
	 * @param deltaTime
	 */
	public void calcPhysicStep(int steps) {
		Position vel = velocity.calcStep(steps);
		setPosition(getPosition().addAndSet(vel));
	}

	public Body setVelocity(Speed velocity) {
		this.velocity = velocity.cpy();
		return this;
	}

	public Speed getVelocity() {
		return this.velocity.cpy();
	}

	public Body setAcceleration(Position acceleration) {
		this.acceleration = acceleration.cpy();
		return this;
	}

}
