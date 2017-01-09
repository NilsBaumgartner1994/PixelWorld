package com.gof.Inputs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.gof.entitys.LocalPlayer;
import com.gof.game.Main;
import com.gof.physics.Direction;

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
		updateABXY();
		updateMouseInputs();
	}

	public void updateMouseInputs() {
		// Player p =
		// Main.getInstance().playerHandler.getPlayerByInput(inputHandlerName);
		// p.shoot = mouseLeft;
		// p.rightClick = mouseRight;
	}

	public void updateABXY() {
		// Player p =
		// Main.getInstance().playerHandler.getPlayerByInput(inputHandlerName);
		// p.jump = keys[Keys.SPACE];
		//
		// if(keys[Keys.Z]){
		// for(int i=0;i<100; i++){
		// Main.getInstance().aiHandler.createAI("AI."+i);
		// }
		// }
	}

	boolean cloud = true;
	
	public LocalPlayer getPlayer(){
		return Main.getInstance().playerHandler.getPlayerByInput(inputHandlerName);
	}

	public void updateLeftStick() {
		Vector2 dir = new Vector2(0, 0);
		LocalPlayer p = getPlayer();

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
		

		p.run(keyboard.isPressed(Keys.SHIFT_LEFT, Keys.SHIFT_RIGHT));
		p.sneak(keyboard.isPressed(Keys.CONTROL_LEFT, Keys.CONTROL_RIGHT));

		p.setLeftStick(dir);
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
		LocalPlayer p = getPlayer();
		if (button == Input.Buttons.LEFT) {
			mouse.left.press();
			p.use(p.cameraController.getGlobalPosFromScreenPos(screenX, p.cameraController.height-screenY));
		}
		if (button == Input.Buttons.RIGHT) {
			mouse.right.press();
		}

		return true;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// Vector3 dir = new Vector3(1, 0, 0);
		// Player p =
		// Main.getInstance().playerHandler.getPlayerByInput(inputHandlerName);
		//
		// float yaw = getYawInDegreeOfModelWithMouse(screenX, screenY,
		// p.getObjPos());
		// dir = dir.rotate(yaw, 0, 1, 0);
		//
		// p.stickRight = dir;

		return true;
	}

	public boolean scrolled(int amount) {
		LocalPlayer p = Main.getInstance().playerHandler.getPlayerByInput(inputHandlerName);
		p.cameraController.changeDistance(amount);
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
