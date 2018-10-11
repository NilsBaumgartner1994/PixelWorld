package com.gentlemansoftware.pixelworld.inputs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.profiles.User;

public class KeyboardHandler {

	public KeyBoard keyboard;
	public Mouse mouse;
	public GamePadLayout keyboardLayout;
	public static String inputHandlerName;

	public KeyboardHandler(InputHandler inputHandler) {
		inputHandlerName = "Keyboard";
		keyboard = new KeyBoard();
		keyboardLayout = new GamePadLayoutKeyboard();
//		keyboardLayout = new GamePadLayoutXBox360();
//		keyboardLayout = new GamePadLayoutPlaystation4();
		mouse = new Mouse();
	}

	public void updateInputLogic() {
		updateLeftStick();
		if (isKeyboardLastInput()) {
			updateButtons();
		}
	}

	private boolean isKeyboardLastInput() {
		User u = getUser();
		return (u.gamepad.layouttype == this.keyboardLayout);
	}

	public User getUser() {
		return Main.getInstance().userHandler.getUserByInput(inputHandlerName);
	}

	public void madeAnAction() {
		User user = getUser();
		user.gamepad.layouttype = keyboardLayout;
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

		float thresholdStick = 0.7f;
		if (dir.len2() < thresholdStick) {
			dir = new Vector2(0, 0);
		} else {
			madeAnAction();
		}

		if (isKeyboardLastInput()) {
			u.gamepad.getLeftStick().setVec(dir.cpy());
		}
	}

	public void updateButtons() {
		User u = getUser();
		u.gamepad.setButtonState(GamePadButtons.SHIFT, keyboard.isPressed(Keys.SHIFT_LEFT, Keys.SHIFT_RIGHT));
		u.gamepad.setButtonState(GamePadButtons.CTRL, keyboard.isPressed(Keys.CONTROL_LEFT, Keys.CONTROL_RIGHT));

		u.gamepad.setButtonState(GamePadButtons.LEFTPAD_DOWN, keyboard.isPressed(Keys.SLASH)); // -
																								// on
																								// mac
		u.gamepad.setButtonState(GamePadButtons.UP, keyboard.isPressed(Keys.RIGHT_BRACKET)); // +
																								// on
																								// mac

		u.gamepad.setButtonState(GamePadButtons.LEFTPAD_LEFT, keyboard.isPressed(Keys.LEFT));
		u.gamepad.setButtonState(GamePadButtons.LEFTPAD_RIGHT, keyboard.isPressed(Keys.RIGHT));

		u.gamepad.setButtonState(GamePadButtons.RIGHTPAD_LEFT, keyboard.isPressed(Keys.J));
		u.gamepad.setButtonState(GamePadButtons.RIGHTPAD_UP, keyboard.isPressed(Keys.I));
		u.gamepad.setButtonState(GamePadButtons.RIGHTPAD_DOWN, keyboard.isPressed(Keys.K));
		u.gamepad.setButtonState(GamePadButtons.RIGHTPAD_RIGHT, keyboard.isPressed(Keys.L));

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
			// u.gamepad.setButtonState(GamePadButtons.R2, true);
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
		u.gamepad.setButtonState(GamePadButtons.LEFTPAD_RIGHT, false);
		u.gamepad.setButtonState(GamePadButtons.LEFTPAD_LEFT, false);

		u.gamepad.setButtonState(GamePadButtons.LEFTPAD_RIGHT, amount > 0 ? true : false);
		u.gamepad.setButtonState(GamePadButtons.LEFTPAD_LEFT, amount < 0 ? true : false);

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
		madeAnAction();
		return false;
	}

}
