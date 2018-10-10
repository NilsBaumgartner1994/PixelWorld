package com.gentlemansoftware.pixelworld.profiles;

import com.badlogic.gdx.Gdx;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.inputs.GamePadButtons;
import com.gentlemansoftware.easyGameNetwork.EasyGameNetwork;
import com.gentlemansoftware.pixelworld.entitys.Bat;
import com.gentlemansoftware.pixelworld.entitys.Human;
import com.gentlemansoftware.pixelworld.game.CameraController2D;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.helper.SplitScreenDimension;
import com.gentlemansoftware.pixelworld.menu.MenuHandler;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.sound.SoundManager;
import com.gentlemansoftware.pixelworld.sound.UserSoundManager;
import com.gentlemansoftware.pixelworld.world.MapTile;
import com.gentlemansoftware.pixelworld.world.TileWorld;
import com.gentlemansoftware.pixelworld.worldgenerator.Amortized2DNoise;

public class User {

	public UserProfile profile;
	public CameraControllerInterface cameraController;
	public UserSoundManager soundManager;
	public GamePad gamepad;
	public Human human;
	public MenuHandler menuHandler;
	public EasyGameNetwork network;

	public User() {
		new UserProfile().save();
		this.profile = UserProfile.load("Default");
		System.out.println(this.profile.name);

		initHandlers();

		network = new EasyGameNetwork(this);

		// Position startPos = new Position(0, 0, 0, 0, 1, 0);

		// this.human = new Human(this.activGameWorld, startPos, "Bob");
		// this.human.spawn();
		// cameraController.setTrack(human);
		//
		//
		// // for (int i = 0; i < 1000; i++) {
		// new Bat(this.activGameWorld, startPos.cpy().addAndSet(2, 0, 0, 0, 1,
		// 0)).spawn();
		//// bat.spawn();
		// // }

	}

	public TileWorld getTileWorld() {
		if (network.gameClient.isConnected()) {
			return network.gameClient.gameWorld;
		} else if (network.gameServer.isAlive()) {
			return network.gameServer.gameWorld;
		}
		return Main.getInstance().titleScreenWorld;
	}

	private void initHandlers() {
		this.gamepad = new GamePad();
		this.menuHandler = new MenuHandler(this);
		this.soundManager = new UserSoundManager(this.profile.soundProfile);
		SplitScreenDimension dim = new SplitScreenDimension(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.cameraController = new CameraController2D(this, dim);
	}

	public void updateControlledEntitys() {
		if (human != null) {
			switch (cameraController.getCameraDirection()) {
			case NORTH:
				human.updateLeftStick(gamepad.getLeftStick().getVec());
				break;
			case EAST:
				human.updateLeftStick(gamepad.getLeftStick().getVec().rotate90(2).scl(-1));
				break;
			case SOUTH:
				human.updateLeftStick(gamepad.getLeftStick().getVec().scl(-1));
				break;
			case WEST:
				human.updateLeftStick(gamepad.getLeftStick().getVec().rotate90(3));
				break;
			default:
				break;
			}
		} else {
			if (gamepad.getLeftStick().getVec().len2() > 0) {
				Direction dir = Direction.getDirectionFromVector(gamepad.getLeftStick().getVec());
				int speed = 4;
				speed = gamepad.isButtonPressed(GamePadButtons.SHIFT) ? speed * 4 : speed;
				int x = dir == Direction.EAST ? 2 * speed : 0;
				x = dir == Direction.WEST ? -2 * speed : x;
				int y = dir == Direction.NORTH ? 1 * speed : 0;
				y = dir == Direction.SOUTH ? -1 * speed : y;

				x = dir == Direction.MIDDLE ? 0 : x;
				y = dir == Direction.MIDDLE ? 0 : y;

				this.cameraController
						.setCameraPosition(this.cameraController.getCameraPosition().addAndSet(0, x, 0, y));
			}
		}
	}

	public void updateUserInputs() {
		if (menuHandler != null && gamepad != null) {
			menuHandler.updateInput(gamepad);
		}
	}

}
