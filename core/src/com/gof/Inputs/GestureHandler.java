package com.redagent.Inputs;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.redagent.game.Main;

public class GestureHandler implements GestureListener {

	private Map<Integer, TouchInfo> touches = new HashMap<Integer, TouchInfo>();

	public GestureHandler(InputHandler inputHandler) {
		for (int i = 0; i < 5; i++) {
			touches.put(i, new TouchInfo());
		}
	}

	public Map<Integer, TouchInfo> getTouches() {
		return touches;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Main.log(getClass(), "Tap");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		Main.log(getClass(), "Long Press");
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		Main.log(getClass(), "Fling");
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Main.log(getClass(), "Pan");
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		Main.log(getClass(), "PanStop");
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		Main.log(getClass(), "Zoom");
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		Main.log(getClass(), "Pinch");
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Main.log(getClass(), "touch");
		return false;
	}

}
