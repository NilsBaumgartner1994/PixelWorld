package com.gentlemansoftware.pixelworld.inputs;

public class GamePad {

	public GamePadLayout layouttype;
	private Stick leftStick;
	private Stick rightStick;
	private Button[] buttons;

	public GamePad() {
		layouttype = new GamePadLayoutKeyboard();
		initValues();
		resetValues();
	}
	
	public Stick getLeftStick(){
		return this.leftStick;
	}
	
	public Stick getRightStick(){
		return this.rightStick;
	}
	
	public void setButtonState(GamePadButtons b, boolean pressed) {
		getButton(b).setState(pressed);
	}

	public void pressButton(GamePadButtons b) {
		getButton(b).press();
	}

	public void releaseButton(GamePadButtons b) {
		getButton(b).release();
	}

	public boolean isButtonTyped(GamePadButtons b) {
		return getButton(b).isTyped();
	}

	public boolean isButtonPressed(GamePadButtons b) {
		return getButton(b).isPressed();
	}

	public Button getButton(GamePadButtons b) {
		return buttons[b.ordinal()];
	}

	private void initValues() {
		this.leftStick = new Stick();
		this.rightStick = new Stick();

		int amountButtons = GamePadButtons.values().length;
		buttons = new Button[amountButtons];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new Button();
		}
	}

	public void resetValues() {
		this.leftStick.reset();
		this.rightStick.reset();
		for (Button b : buttons) {
			b.reset();
		}
	}

	public String toString() {
		String buttonStates = "";
		for (GamePadButtons b : GamePadButtons.values()) {
			buttonStates += b.name() + ":" + (isButtonPressed(b) ? "T" : "F") + " ";
		}
		return "L:" + leftStick.getVec().toString() + " |R:" + rightStick.getVec().toString() + " " + buttonStates;
	}

}
