package com.gof.Inputs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.gof.entitys.Human;
import com.gof.game.Main;
import com.gof.physics.Direction;
import com.gof.profiles.User;

public class KeyboardHandler {

	public KeyBoard keyboard;
	public Mouse mouse;

	public static String inputHandlerName;

	public KeyboardHandler(InputHandler inputHandler) {
		inputHandlerName = "Keyboard";
		keyboard = new KeyBoard();
		mouse = new Mouse();
	}

	public void updateInputLogic() {
		updateLeftStick();
	}
	
	public User getUser(){
		return Main.getInstance().userHandler.getUserByInput(inputHandlerName);
	}

	public void updateLeftStick() {
		Vector2 dir = new Vector2(0, 0);
		User u = getUser();

		if (keyboard.isPressed(Keys.A)) {
			dir.add(new Vector2(-1, 0)); // left
		}
		if (keyboard.isPressed(Keys.D)) {
			dir.add(new Vector2(1, 0)); // right
		}
		if (keyboard.isPressed(Keys.W)) {
			dir.add(new Vector2(0, 1)); // up
		}
		if (keyboard.isPressed(Keys.S)) {
			dir.add(new Vector2(0, -1)); // down
		}
		u.gamepad.setLeftStick(dir);
		
		u.gamepad.setButtonState(GamePadButtons.SHIFT, keyboard.isPressed(Keys.SHIFT_LEFT, Keys.SHIFT_RIGHT));
		u.gamepad.setButtonState(GamePadButtons.CTRL, keyboard.isPressed(Keys.CONTROL_LEFT, Keys.CONTROL_RIGHT));
		u.gamepad.setButtonState(GamePadButtons.ESC, keyboard.isPressed(Keys.ESCAPE));
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			mouse.left.release();
		}
		if (button == Input.Buttons.RIGHT) {
			mouse.right.release();
		}
		return true;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		mouse.updatePosition(screenX, screenY);

		return true;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		User u = getUser();
		if (button == Input.Buttons.LEFT) {
			mouse.left.press();
//			p.use(u.cameraController.getGlobalPosFromScreenPos(screenX, u.cameraController.height-screenY));
		}
		if (button == Input.Buttons.RIGHT) {
			mouse.right.press();
		}

		return true;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mouseMoved(screenX,screenY);
		touchDown(screenX,screenY,pointer,Input.Buttons.LEFT);
		
		
		return true;
	}

	public boolean scrolled(int amount) {
//		Human p = this.getPlayer();
//		p.inventory.setActivSlot(p.inventory.getActivSlot()+amount);
		return true;
	}

	public boolean keyTyped(char character) {
		User u = this.getUser();
		switch(character){
			case '+' : u.cameraController.changeDistance(-1); break;
			case '-' : u.cameraController.changeDistance(1); break;
			default : break;
		}
		return true;
	}

	public boolean keyUp(int keycode) {
		keyboard.key(keycode).release();
		return false;
	}

	public boolean keyDown(int keycode) {
		keyboard.key(keycode).press();
		return false;
	}

}
