package com.gentlemansoftware.pixelworld.profiles;

import com.badlogic.gdx.Gdx;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.entitys.Bat;
import com.gentlemansoftware.pixelworld.entitys.Human;
import com.gentlemansoftware.pixelworld.game.CameraController2D;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.menu.MenuHandler;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.world.MapTile;
import com.gentlemansoftware.pixelworld.world.TileWorld;

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
		this.human.spawn();
		cameraController.setTrack(human);
		
		Bat bat = new Bat(this.activGameWorld,startPos.cpy().addAndSet(2, 0, 0, 0));
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
