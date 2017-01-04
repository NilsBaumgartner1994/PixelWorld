package com.gof.Inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class InputHandler implements InputProcessor, GestureListener {

	public ControllerHandler controllerHandler;
	public KeyboardHandler keyboardHandler;
	public GestureHandler gestureHandler;

	public InputHandler() {
		// GamePad
		controllerHandler = new ControllerHandler(this);
		Controllers.addListener(controllerHandler); // Activate as extra Input

		// Keyboard + Mouse
		keyboardHandler = new KeyboardHandler(this); // Keyboard class
		gestureHandler = new GestureHandler(this); // Touch class

		// After initilize set Input as this
		Gdx.input.setInputProcessor(this); // last
	}

	public void updateInputLogic() {
		updateHandlerInputs();
	}
	
	private void updateHandlerInputs(){
		keyboardHandler.updateInputLogic();
//		controllerHandler.updateInputLogic();
	}

	///////////////////////////
	// Leite Methoden Weiter //
	///////////////////////////

	@Override
	public boolean keyDown(int keycode) {
		return keyboardHandler.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return keyboardHandler.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		return keyboardHandler.keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return keyboardHandler.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return keyboardHandler.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return keyboardHandler.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return keyboardHandler.mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled(int amount) {
		return keyboardHandler.scrolled(amount);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return gestureHandler.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return gestureHandler.tap(x, y, count, button);
	}

	@Override
	public boolean longPress(float x, float y) {
		return gestureHandler.longPress(x, y);
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return gestureHandler.fling(velocityX, velocityY, button);
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return gestureHandler.pan(x, y, deltaX, deltaY);
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return gestureHandler.panStop(x, y, pointer, button);
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return gestureHandler.zoom(initialDistance, distance);
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return gestureHandler.pinch(initialPointer1, initialPointer2, pointer1, pointer2);
	}

	@Override
	public void pinchStop() {
		// TODO Auto-generated method stub
		
	}

}
