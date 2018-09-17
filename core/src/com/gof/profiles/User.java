package com.gof.profiles;

import com.badlogic.gdx.Gdx;
import com.gof.inputs.GamePad;
import com.gof.entitys.Bat;
import com.gof.entitys.Human;
import com.gof.game.CameraController2D;
import com.gof.game.CameraControllerInterface;
import com.gof.game.Main;
import com.gof.menu.MenuHandler;
import com.gof.physics.Position;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

public class User {

	public UserProfile profile;
	public CameraControllerInterface cameraController;
	public GamePad gamepad;
	public TileWorld activGameWorld;
	public Human human;
	public MenuHandler menuHandler;

	public User() {
		new UserProfile().save();
		this.profile = UserProfile.load("Default");
		System.out.println(this.profile.name);
		
		this.gamepad = new GamePad();
		this.menuHandler = new MenuHandler(this);

		this.activGameWorld = Main.getInstance().titleScreenWorld;
		initCamera();
		
//		Position startPos = new Position(51721, MapTile.tileWidth / 2, 50811, MapTile.tileHeight / 2);
//		Position startPos = new Position(Integer.MAX_VALUE, MapTile.tileWidth / 2, Integer.MAX_VALUE, MapTile.tileHeight / 2);
		Position startPos = new Position(0, MapTile.tileWidth / 2, 0, MapTile.tileHeight / 2);
		
		this.human = new Human(this.activGameWorld, startPos, "Bob");
		cameraController.setTrack(human);
		
//		Bat bat = new Bat(this.activGameWorld,startPos.cpy().addAndSet(2, 0, 0, 0));
//		bat.spawn();
	}

	public void initCamera() {
		cameraController = new CameraController2D(this, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// cameraController.setTrack(this);
	}

	public void updateUserInputs() {
		if (menuHandler != null && gamepad != null) {
			menuHandler.updateInput(gamepad);
		}
	}

}
