package com.gentlemansoftware.pixelworld.inputs;

import com.badlogic.gdx.math.Vector2;
import com.gentlemansoftware.pixelworld.physics.Direction;

public class Stick {

	private Vector2 vec;
	private float threshold = 0.2f;
	public static final int STICKMAX = 1;
	private Button state;
	private Direction lastDirection;
	
	public Stick(){
		state = new Button();
		lastDirection = Direction.MIDDLE;
		setVec(new Vector2(0, 0));
	}
	
	public void reset(){
		setVec(new Vector2(0, 0));
		state.reset();
		lastDirection = Direction.MIDDLE;
	}
	
	private boolean isBackToOrigin(){
		return state.isTyped();
	}
	
	public Direction getLastDirection(){
		if(isBackToOrigin()){
			Direction helper = lastDirection;
			lastDirection = Direction.MIDDLE;
			return helper;
		}
		return Direction.MIDDLE;
	}
	
	public Direction getStickDirection(){
		if(state.isPressed()){
			return Direction.getDirectionFromVector(getVec());
		}
		return Direction.MIDDLE;
	}

	public void addVec(Vector2 v) {
		setVec(getVec().add(v));
	}
	
	public void setVec(Vector2 v) {
		this.vec = v.cpy().clamp(0, STICKMAX);
		if(getVec().len()>=this.threshold){
			state.press();
			lastDirection = Direction.getDirectionFromVector(getVec());
		} else{
			state.release();
		}
	}
	
	public Vector2 getVec() {
		return this.vec.cpy();
	}
	
}
