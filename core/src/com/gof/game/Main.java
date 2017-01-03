package com.redagent.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.redagent.Inputs.InputHandler;
import com.redagent.entitys.LocalPlayer;
import com.redagent.entitys.LocalPlayerHandler;
import com.redagent.world.TileWorld;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	ResourceLoader resourceLoader;
	TileWorld tileWorld;

	public static void log(Class<?> c, String log) {
		System.out.println(c.getSimpleName() + ": " + log);
	}

	public Sprite sprite, sprite2;

	Matrix4 debugMatrix;
	OrthographicCamera camera;
	


	final static float PIXELS_TO_METERS = 100f;
	
	
	private static Main instance;
	public LocalPlayerHandler playerHandler;
	public InputHandler inputHandler;

	public void initResourceLoader() {
		resourceLoader = new ResourceLoader();
	}

	public static Main getInstance() {
		return instance;
	}

	public void initTileWorld() {
		tileWorld = new TileWorld();
	}
	
	public void initPlayerHandler() {
		playerHandler = new LocalPlayerHandler();
	}

	public void initInputHandler() {
		inputHandler = new InputHandler();
	}

//		bodyDef.position.set(51721, 50811);

	@Override
	public void create() {
		instance = this;
		initResourceLoader();
		initTileWorld();
		initPlayerHandler();
		initInputHandler();

		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	@Override
	public void resize(int width, int height) {
//	    viewport.update(width, height);
	}

	@Override
	public void render() {
		inputHandler.updateInputLogic();
		
		camera.update();
		// Step the physics simulation forward at a rate of 60hz
//		world.step(1f / 60f, 6, 2);
		
		updateEntitysInputs();
//		updateCloudPositions();

		float deltaTime = Gdx.graphics.getDeltaTime();
		updatePhysics(deltaTime);
		
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderForPlayers();
	}
		
	public void updateEntitysInputs() {
		LocalPlayer[] players = playerHandler.getPlayers();

		for (LocalPlayer p : players) {
			p.updateMyGameObjects();
		}
	}
	
	public void updatePhysics(float deltaTime) {
		updateEntitysBodys(deltaTime);
	}
	
	public void updateEntitysBodys(float deltaTime) {
		LocalPlayer[] players = playerHandler.getPlayers();

		for (LocalPlayer p : players) {
			p.calcPhysicStep(deltaTime);
		}
	}

	public void renderForPlayers() {
		LocalPlayer[] players = playerHandler.getPlayers();

		for (LocalPlayer p : players) {
			p.cameraController.renderToFrameBuffer();
			p.cameraController.renderToInformationBuffer();
			p.cameraController.renderToScreen();
		}
	}

	@Override
	public void dispose() {
		LocalPlayer[] players = playerHandler.getPlayers();

		for (LocalPlayer p : players) {
			p.cameraController.dispose();
		}
	}
}
