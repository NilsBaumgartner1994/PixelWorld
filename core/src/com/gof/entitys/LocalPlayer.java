package com.redagent.entitys;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.redagent.game.CameraController;
import com.redagent.game.Main;
import com.redagent.helper.VectorHelper;
import com.redagent.materials.Water;
import com.redagent.menu.MenuHandler;
import com.redagent.physics.Direction;
import com.redagent.physics.Speed;
import com.redagent.world.MapTile;
import com.redagent.world.TileWorld;

public class LocalPlayer extends Entity {

	public String name;

	/**
	 * InputVariables
	 */
	private Vector2 stickLeft;
	public boolean stickLeftDown;

	private Vector2 stickRight;
	public boolean stickRightDown;

	public CameraController cameraController;
	public MenuHandler menuHandler;

	private float speed;
	
	private boolean sneaking;

	public int direction;

	public LocalPlayer(String name) {
		super(51721, 50811);
		speed = Speed.walkSpeed;
		this.name = name;
		menuHandler = new MenuHandler(this);
		direction = Direction.SOUTH;
		sneaking = false;
		initCamera();
		resetInputVariables();
	}

	public void sneak(boolean sneak){
		sneaking = sneak;
		if(sneaking){
			speed = Speed.sneakSpeed;
		}
	}

	public void run(boolean run) {
		// true : false
		if (!sneaking) {
			speed = run ? Speed.runSpeed : Speed.walkSpeed;
		}
	}

	public void initCamera() {
		cameraController = new CameraController(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraController.setTrack(this);
	}

	public void setLeftStick(Vector2 dir) {		
		this.stickLeft = dir.cpy();
	}
	
	public void setRightStick(Vector2 dir){

	}
	
	private void updateLeftStick() {
		if (this.stickLeft.len() != 0) {
			this.direction = VectorHelper.getDirFromVector(this.stickLeft);
		}

		Vector2 dir = this.stickLeft.cpy();
		dir.scl(speed);
		setVelocity(dir);
	}

	public void resetInputVariables() {
		stickLeft = new Vector2();
		stickRight = new Vector2();
	}

	@Override
	public List<Sprite> getSprite() {
		return PlayerSpriteCreator.getPlayerSprite(this);
	}

	public void updateMyGameObjects() {
		updateLeftStick();
	}

}
