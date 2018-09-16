package com.gof.inputs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.gof.entitys.Human;
import com.gof.game.Main;
import com.gof.helper.EasyColor;
import com.gof.menuComponents.ControllerButtonOverlay;
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

	public User getUser() {
		return Main.getInstance().userHandler.getUserByInput(inputHandlerName);
	}

	public void updateLeftStick() {
		Vector2 dir = new Vector2(0, 0);
		User u = getUser();

		if (keyboard.isPressed(Keys.A) || keyboard.isPressed(Keys.LEFT)) {
			dir.add(new Vector2(-1, 0)); // left
		}
		if (keyboard.isPressed(Keys.D) || keyboard.isPressed(Keys.RIGHT)) {
			dir.add(new Vector2(1, 0)); // right
		}
		if (keyboard.isPressed(Keys.W) || keyboard.isPressed(Keys.UP)) {
			dir.add(new Vector2(0, 1)); // up
		}
		if (keyboard.isPressed(Keys.S) || keyboard.isPressed(Keys.DOWN)) {
			dir.add(new Vector2(0, -1)); // down
		}
		u.gamepad.getLeftStick().setVec(dir);

		u.gamepad.setButtonState(GamePadButtons.SHIFT, keyboard.isPressed(Keys.SHIFT_LEFT, Keys.SHIFT_RIGHT));
		u.gamepad.setButtonState(GamePadButtons.CTRL, keyboard.isPressed(Keys.CONTROL_LEFT, Keys.CONTROL_RIGHT));
		
		u.gamepad.setButtonState(GamePadButtons.DOWN, keyboard.isPressed(Keys.SLASH)); //- on mac
		u.gamepad.setButtonState(GamePadButtons.UP, keyboard.isPressed(Keys.RIGHT_BRACKET)); //+ on mac
		
		u.gamepad.setButtonState(GamePadButtons.LEFT, keyboard.isPressed(Keys.LEFT));
		u.gamepad.setButtonState(GamePadButtons.RIGHT, keyboard.isPressed(Keys.RIGHT));
		
		u.gamepad.setButtonState(GamePadButtons.X, keyboard.isPressed(Keys.J));
		u.gamepad.setButtonState(GamePadButtons.Y, keyboard.isPressed(Keys.I));
		u.gamepad.setButtonState(GamePadButtons.A, keyboard.isPressed(Keys.K));
		u.gamepad.setButtonState(GamePadButtons.B, keyboard.isPressed(Keys.L));
		
		
		u.gamepad.setButtonState(GamePadButtons.ESC, keyboard.isPressed(Keys.ESCAPE));
		u.gamepad.setButtonState(GamePadButtons.R2, keyboard.isPressed(Keys.E));
		u.gamepad.setButtonState(GamePadButtons.START, keyboard.isPressed(Keys.ENTER));
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		User u = getUser();
		
		if (button == Input.Buttons.LEFT) {
			mouse.left.release();
			u.gamepad.setButtonState(GamePadButtons.R2, false);
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
//			u.gamepad.setButtonState(GamePadButtons.R2, true);
			// p.use(u.cameraController.getGlobalPosFromScreenPos(screenX,
			// u.cameraController.height-screenY));
		}
		if (button == Input.Buttons.RIGHT) {
			mouse.right.press();
		}

		return true;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mouseMoved(screenX, screenY);
		touchDown(screenX, screenY, pointer, Input.Buttons.LEFT);

		return true;
	}

	public boolean scrolled(int amount) {
		User u = getUser();
		u.gamepad.setButtonState(GamePadButtons.RIGHT,false);
		u.gamepad.setButtonState(GamePadButtons.LEFT, false);
		
		u.gamepad.setButtonState(GamePadButtons.RIGHT, amount > 0 ? true : false);
		u.gamepad.setButtonState(GamePadButtons.LEFT, amount < 0 ? true : false);

		return true;
	}

	public boolean keyTyped(char character) {
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
