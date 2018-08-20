package com.gof.Inputs;

import com.badlogic.gdx.math.Vector2;
import com.gof.profiles.User;

//This code was taken from http://www.java-gaming.org/index.php?topic=29223.0
//With thanks that is!

public class GamePad {
	
	public static final int STICKMAX = 0;
	private Vector2 leftStick;
	private Vector2 rightStick;
	private Button[] buttons;
	
	public GamePad(){
		initValues();
		resetValues();
	}
	
	public void setLeftStick(Vector2 v){
		this.leftStick = v.cpy().clamp(0, STICKMAX);
	}
	
	public Vector2 getLeftStick(){
		return this.leftStick.cpy();
	}
	
	public void addLeftStick(Vector2 v){
		setLeftStick(getLeftStick().add(v));
	}
	
	public void setRightStick(Vector2 v){
		this.rightStick = v.cpy().clamp(0, STICKMAX);
	}
	
	public Vector2 getRightStick(){
		return this.rightStick.cpy();
	}
	
	public void addRightStick(Vector2 v){
		setRightStick(getRightStick().add(v));
	}
	
	public void setButtonState(GamePadButtons b, boolean pressed){
		getButton(b).setState(pressed);
	}
	
	public void pressButton(GamePadButtons b){
		getButton(b).press();
	}
	
	public void releaseButton(GamePadButtons b){
		getButton(b).release();
	}
	
	public boolean isButtonPressed(GamePadButtons b){
		return getButton(b).isPressed();
	}
	
	public Button getButton(GamePadButtons b){
		return buttons[b.ordinal()];
	}
	
	private void initValues(){
		setLeftStick(new Vector2(0,0));
		setRightStick(new Vector2(0,0));
		
		int amountButtons = GamePadButtons.values().length;
		buttons = new Button[amountButtons];
		for(int i=0; i<buttons.length;i++){
			buttons[i] = new Button();
		}
	}
	
	public void resetValues(){
		setLeftStick(new Vector2(0,0));
		setRightStick(new Vector2(0,0));
		for(Button b : buttons){
			b.reset();
		}
	}
	
}
