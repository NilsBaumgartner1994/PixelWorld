package com.gof.entitys;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.CameraController;
import com.gof.game.Main;
import com.gof.helper.VectorHelper;
import com.gof.materials.Water;
import com.gof.menu.MenuHandler;
import com.gof.physics.Direction;
import com.gof.physics.Position;
import com.gof.physics.Speed;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

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

	private int speed;
	
	private boolean sneaking;

	public Position direction;

	public LocalPlayer(String name) {
		super(51721, 50811);
//		speed = Speed.walkSpeed;
		this.name = name;
		menuHandler = new MenuHandler(this);
		sneaking = false;
		initCamera();
		resetInputVariables();
	}

	public void sneak(boolean sneak){
		sneaking = sneak;
		if(sneaking){
			speed = Speed.sneak;
		}
	}

	public void run(boolean run) {
		// true : false
		if (!sneaking) {
			speed = run ? Speed.run : Speed.walk;
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
			this.direction = Position.getPositionDirectionFromVector(this.stickLeft);
			setVelocity(this.direction.cpy().scale(this.speed));
		}
		else{
		setVelocity(Direction.STOP);
		}
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
