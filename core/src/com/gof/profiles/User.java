package com.gof.profiles;

import com.badlogic.gdx.Gdx;
import com.gof.Inputs.GamePad;
import com.gof.Inputs.GamePadButtons;
import com.gof.entitys.Human;
import com.gof.game.CameraController;
import com.gof.game.Main;
import com.gof.menu.MenuHandler;
import com.gof.world.TileWorld;

public class User {

	private UserProfile profile;
	public CameraController cameraController;
	public GamePad gamepad;
	public TileWorld activGameWorld;
	public Human human;
	public MenuHandler menuHandler;

	public User() {
		this.profile = new UserProfile();
		this.gamepad = new GamePad();
		this.menuHandler = new MenuHandler(this);

		this.activGameWorld = Main.getInstance().titleScreenWorld;
		initCamera();
		this.human = new Human(this.activGameWorld);
		cameraController.setTrack(human);
	}

	public void initCamera() {
		cameraController = new CameraController(this, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// cameraController.setTrack(this);
	}

	public void updateUserInputs() {
		if (menuHandler != null && gamepad != null) {
			menuHandler.updateInput(gamepad);
		}
	}

}
