package com.gentlemansoftware.pixelworld.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.gentlemansoftware.pixelworld.inputs.InputHandler;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.profiles.UserHandler;
import com.gentlemansoftware.pixelworld.sound.SoundManager;
import com.gentlemansoftware.pixelworld.world.TileWorld;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	public ResourceLoader resourceLoader;
	public SoundManager soundManager;
	public FileController fileController;
	public AssetManager assets;
	public TileWorld titleScreenWorld;

	public static void log(Class<?> c, String log) {
		System.out.println(c.getSimpleName() + ": " + log);
	}

	public Sprite sprite, sprite2;

	Matrix4 debugMatrix;
	OrthographicCamera camera;

	final static float PIXELS_TO_METERS = 100f;

	private static Main instance;
	public UserHandler userHandler;
	public InputHandler inputHandler;

	public void initAssetAndResourceLoader() {
		assets = new AssetManager();
		resourceLoader = new ResourceLoader();
		soundManager = new SoundManager();
		fileController = new FileController();
	}

	public static Main getInstance() {
		return instance;
	}

	public void initTileWorld() {
		titleScreenWorld = new TileWorld("Default");
	}

	public void initPlayerHandler() {
		userHandler = new UserHandler();
	}

	public void initInputHandler() {
		inputHandler = new InputHandler();
	}

	@Override
	public void create() {
		instance = this;
		initAssetAndResourceLoader();
		initTileWorld();
		initPlayerHandler();
		initInputHandler();

		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		hideMouse();
	}

	private void hideMouse() {
		Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("data/gui/cursor/hand_blank.png")), 0, 0);
		Gdx.graphics.setCursor(customCursor);
	}

	@Override
	public void resize(int width, int height) {
		userHandler.adaptScreenSizeForAllPlayers();
	}	

	public void updateWorlds() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		this.titleScreenWorld.timePassed(deltaTime);
		for(User user : userHandler.getUsers()){
			user.getTileWorld().timePassed(deltaTime);
		}
	}

	@Override
	public void render() {
		inputHandler.updateInputLogic();

		camera.update();
		userHandler.updateUserInputs();

		updateWorlds();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderForUsers();
	}

	public void renderForUsers() {
		User[] users = userHandler.getUsers();

		for (User u : users) {
			u.cameraController.render();
		}
	}

	@Override
	public void dispose() {
		User[] users = userHandler.getUsers();

		for (User u : users) {
			u.cameraController.dispose();
		}
		
		this.assets.dispose();
		this.soundManager.disposeAll();
	}
}