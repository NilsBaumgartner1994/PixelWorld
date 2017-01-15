package com.gof.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.gof.Inputs.InputHandler;
import com.gof.entitys.LocalPlayer;
import com.gof.entitys.LocalPlayerHandler;
import com.gof.world.TileWorld;

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
		
		hideMouse();
	}
	
	private void hideMouse(){
		Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("data/gui/hand_blank.png")), 0, 0);
		Gdx.graphics.setCursor(customCursor);
	}
	
	@Override
	public void resize(int width, int height) {
//	    viewport.update(width, height);
	}
	
	float renderTime = 0;
	float refreshRate = 1/60f;
	
	float timeSpeed = 1f;

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		deltaTime*=timeSpeed;
		
		renderTime+=deltaTime;
		
		
		
		int steps = 0;
		if(renderTime>=refreshRate){
			steps = (int) (renderTime/refreshRate);
		}
		renderTime%=refreshRate;
		
		inputHandler.updateInputLogic();
		
//		Main.log(getClass(), "RenderTime: "+renderTime+" | DeltaTime: "+deltaTime+" | Steps: "+steps);
		
		camera.update();
		updateEntitysInputs();
		
		// Step the physics simulation forward at a rate of 60hz
		
		updatePhysics(steps);
		
		
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
	
	public void updatePhysics(int steps) {
		updateEntitysBodys(steps);
	}
	
	public void updateEntitysBodys(int steps) {
		LocalPlayer[] players = playerHandler.getPlayers();
		
//		Main.log(getClass(), "DeltaTime: "+deltaTime+" 1/60="+frac*60);

		for (LocalPlayer p : players) {
			p.calcPhysicStep(steps);
		}
	}

	public void renderForPlayers() {
		LocalPlayer[] players = playerHandler.getPlayers();

		for (LocalPlayer p : players) {
			p.cameraController.renderToFrameBuffer();
			p.cameraController.renderToInformationBuffer();
			p.cameraController.renderGUI();
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
