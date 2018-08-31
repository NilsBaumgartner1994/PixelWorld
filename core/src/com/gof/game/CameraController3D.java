package com.gof.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.gof.inputs.Mouse;
import com.gof.entitys.Entity;
import com.gof.physics.Body;
import com.gof.physics.Position;
import com.gof.profiles.User;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

public class CameraController3D implements CameraControllerInterface {

	public static final int pixelLevel = 1;

	public Camera cam;

	public AssetManager assets;
	public SpriteBatch spriteBatch;
	public ModelBatch modelBatch;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public Environment environment;

	public Body cameraBody;

	private FrameBuffer fboInformation;
	private FrameBuffer fboWorld;
	private FrameBuffer fbUI;

	public BitmapFont font;

	public int width;
	public int height;

	public static int zoomLevel = 0;
	public static int zoomLevelmin = -2;
	public static int zoomLevelmax = 3;

	public boolean showInformations = true;

	public GlyphLayout layout;
	private Entity track;
	private User user;

	public CameraController3D(User localUser, int width, int height) {
		resize(width, height);
		this.user = localUser;

		initFont();
		initEnvironment();
		initFrameBuffer();
		initCamera();
	}

	private void initFont() {
		font = new BitmapFont();
		font.setColor(Color.YELLOW);
		layout = new GlyphLayout();
	}

	public void resize(int width, int height) {
		setScreenSize(width, height);
	}

	private void setScreenSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	private void initEnvironment() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	private void initCamera() {
		cameraBody = new Body();

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
	}

	public void initializeFBOWorld() {
		if (fboWorld != null)
			fboWorld.dispose();

		fboWorld = new FrameBuffer(Pixmap.Format.RGB888, this.getWidth() / pixelLevel, this.getHeigth() / pixelLevel,
				true);

		fboWorld.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}

	private void initFrameBuffer() {
		fboInformation = new FrameBuffer(Format.RGBA8888, width, height, true);
		fboInformation.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		fbUI = new FrameBuffer(Format.RGBA8888, width, height, true);
		fbUI.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		if (spriteBatch != null)
			spriteBatch.dispose();
		spriteBatch = new SpriteBatch();

		if (modelBatch != null)
			modelBatch.dispose();
		modelBatch = new ModelBatch();

		initializeFBOWorld();
	}
	
	CameraInputController camController;

	public void render() {
		line = 1;
		renderWorld();
		// renderToFrameBuffer();
		renderToInformationBuffer();
		// renderGUI();
		renderToScreen();
	}

	public void renderWorld() {
		fboWorld.begin();

		
		Gdx.gl.glEnable(GL20.GL_CULL_FACE);
		Gdx.gl.glCullFace(GL20.GL_FRONT); //////////// This line

		Gdx.gl.glViewport(0, 0, fboWorld.getWidth(), fboWorld.getHeight());
		
		Gdx.gl.glClearColor(1, 1, 1, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		updateCameraPosition();
		addWorldTilesToInstances();
		// Draw all model instances using the camera
		modelBatch.begin(cam);
		modelBatch.render(instances, environment);
		modelBatch.end();

		// Finish rendering into the framebuffer
		fboWorld.end();
	}

	private void addWorldTilesToInstances() {
		instances = new Array<ModelInstance>();
		
		int amount = 15;

		TileWorld world = this.user.activGameWorld;
		Position camPos = this.cameraBody.getPosition();
		if (world != null) {
			for (int x = -amount; x <= amount; x += 1) {
				for (int y = -amount; y <= amount; y += 1) {
					int globalX = camPos.x + x;
					int globalY = camPos.y + y;
					MapTile tile = world.getMapTileFromGlobalPos(globalX, globalY);
					if (tile != null) {
						world.activateChunk(tile.chunk);
						Model m = tile.getModel();
						if (m != null) {
							
							ModelInstance instance = new ModelInstance(m);
							instance.transform.scale(.5f, .5f, .5f);
							instance.transform.setToTranslation(globalX, 0, globalY);
							instances.add(instance);
						}
					}
				}
			}
		}
	}
	
	private static int dinstanceLength = 10;
	private static Vector3 distance = new Vector3(dinstanceLength, dinstanceLength, 0);
	private static float degCam = 0;

	private void updateCameraPosition() {
		if (track != null) {
			cameraBody.setPosition(track);
		}

		Vector3 lookAt = cameraBody.getPosition().getVector3();
		cam.lookAt(lookAt);
		cam.position.set(lookAt.cpy().add(distance));
		cam.update();
	}
	
	public void rotateCamera(float deg){
		degCam=(degCam+deg)%360;
		System.out.println("Rotate Cam: "+degCam);
		distance = new Vector3(dinstanceLength, 0, 0);
		distance.rotate(degCam, 0, 1, 0);
		distance.set(distance.x, dinstanceLength, distance.z);
		Vector3 lookAt = cameraBody.getPosition().getVector3();
		cam.position.set(lookAt.cpy().add(distance));
		cam.up.set(Vector3.Y);
		cam.update();
	}

	public void drawTileWorld(TileWorld world) {

		int amountToShow = 2;

		int xStart = cameraBody.getPosition().x - amountToShow;
		int yStart = cameraBody.getPosition().y - amountToShow;
		int xEnd = xStart + amountToShow * 2;
		int yEnd = yStart + amountToShow * 2;

		List<MapTile> area = world.getArea(xStart, yStart, xEnd, yEnd);

		area = new ArrayList<MapTile>();

		int xcenter = cameraBody.getPosition().x;
		int ycenter = cameraBody.getPosition().y;

		int safetytiles = 7;

		// int breite = this.width / scaleZoom(MapTile.tileWidth) + safetytiles;
		// int hoehe = this.height / scaleZoom(MapTile.tileHeight) +
		// safetytiles;
		int breite = 5;
		int hoehe = 5;

		for (int a = -hoehe + 1; a < hoehe; a++) {
			for (int b = -breite + 1; b < breite; b++) {
				if ((b & 1) != (a & 1))
					continue;
				int x = (a + b) / 2;
				int y = (a - b) / 2;
				MapTile m = world.getMapTileFromGlobalPos(xcenter + x, ycenter + y);
				if (m != null) {
					area.add(m);
				}
			}
		}

		numerator = getZoomLevelScaleFactorNumerator();
		denumerator = getZoomLevelScaleFactorDenumerator();
	}

	public int scaleZoom(int orginalPixel) {
		return orginalPixel * numerator / denumerator;
	}

	int numerator = getZoomLevelScaleFactorNumerator();
	int denumerator = getZoomLevelScaleFactorDenumerator();

	public void drawSprite(Sprite sprite) {
		spriteBatch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(),
				sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
	}

	static int line;

	public void renderToInformationBuffer() {
		if (showInformations) {
			
			fboInformation.begin();
			spriteBatch.begin();
			Gdx.gl.glViewport(0, 0, fboInformation.getWidth(), fboInformation.getHeight());

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			

			// int x = (int) camera.position.x;
			// int y = (int) camera.position.y;

			drawInformationLine("FPS: " + Gdx.graphics.getFramesPerSecond());
			drawInformationLine("Player: " + Main.getInstance().userHandler.getUserNumber(this.user));
			drawInformationLine("GamePad: " + this.user.gamepad.toString());
			drawInformationLine("Zoom: " + zoomLevel);
			TileWorld world = this.user.activGameWorld;
			if (world != null) {
				drawTileWorldInformations(world);
				drawInformationLine("PlayerPos: " + this.user.human.getPosition().toString());
				drawInformationLine("Cam Position: " + this.cam.position.toString());
			} else {
				drawInformationLine("World: " + "None");
			}

			Mouse m = Main.getInstance().inputHandler.keyboardHandler.mouse;

			if (m != null) {

			}

			spriteBatch.end();
			fboInformation.end();
		}
	}

	private void drawTileWorldInformations(TileWorld world) {
		float light = world.time.getLightIntense();
		drawInformationLine("Time: " + world.time.getTicks() + " - Light: " + light);
		drawInformationLine("ShaddowAngle: " + world.time.getShaddowAngle());
	}

	private void drawInformationLine(String s) {
		float z = font.getLineHeight();
		font.draw(spriteBatch, s, 10, height - line * z);
		line++;
	}

	public void renderGUI() {
		fbUI.begin();

		Gdx.gl.glViewport(0, 0, fbUI.getWidth(), fbUI.getHeight());

		// nicht clearen

//		spriteBatch.enableBlending();
		spriteBatch.begin();

		Gdx.gl.glClearColor(1, 1, 1, 1f);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
		// GL20.GL_DEPTH_BUFFER_BIT);

		drawMenu();
		drawMouseIcon();

		spriteBatch.end();
		fbUI.end();
	}

	public void drawMenu() {
		this.user.menuHandler.renderActivMenu(this);
	}

	public int drawSpriteAndSubtractYpos(Sprite s, int xpos, int ypos) {
		ypos -= s.getRegionHeight();
		s.setPosition(xpos, ypos);
		drawSprite(s);
		return ypos;
	}

	private void drawMouseIcon() {
		Mouse m = Main.getInstance().inputHandler.keyboardHandler.mouse;

		Sprite hand = new Sprite(ResourceLoader.getInstance().getGUI("hand_select"));

		if (m != null) {
			hand.setPosition(m.getX() - hand.getRegionWidth() / 2, this.height - m.getY() - hand.getRegionHeight() / 2);
			spriteBatch.draw(hand, hand.getX(), hand.getY(), hand.getOriginX(), hand.getOriginY(), hand.getWidth(),
					hand.getHeight(), hand.getScaleX(), hand.getScaleY(), hand.getRotation());
		}
	}

	public void renderToScreen() {
		spriteBatch.begin();
		spriteBatch.draw(fboWorld.getColorBufferTexture(), 0, 0, width, height, 0, 0, 1, 1);
		spriteBatch.draw(fboInformation.getColorBufferTexture(), 0, 0, width, height, 0, 0, 1, 1);
		spriteBatch.draw(fbUI.getColorBufferTexture(), 0, 0, width, height, 0, 0, 1, 1);
		spriteBatch.end();
	}

	public void distanceIncrease() {
		changeDistance(1);
	}

	public void distanceDecrease() {
		changeDistance(-1);
	}

	public void dispose() {
		fboInformation.dispose();
		spriteBatch.dispose();
		modelBatch.dispose();
		instances.clear();
		fboWorld.dispose();
	}

	public void changeDistance(int amount) {
		zoomLevel += amount;
		if (zoomLevel < zoomLevelmin)
			zoomLevel = zoomLevelmin;
		if (zoomLevel > zoomLevelmax)
			zoomLevel = zoomLevelmax;
	}

	private int getZoomLevelScaleFactorDenumerator() {
		double amount = Math.pow(2, zoomLevel);
		if (amount < 1) {
			return 1;
		}

		return (int) amount;
	}

	private int getZoomLevelScaleFactorNumerator() {
		double amount = Math.pow(2, zoomLevel);
		if (amount < 1) {
			return (int) (1.0 / amount);
		}

		return 1;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	@Override
	public int getHeigth() {
		// TODO Auto-generated method stub
		return this.height;
	}

	@Override
	public BitmapFont getFont() {
		// TODO Auto-generated method stub
		return this.font;
	}

	@Override
	public GlyphLayout getLayout() {
		// TODO Auto-generated method stub
		return this.layout;
	}

	@Override
	public SpriteBatch getSpriteBatch() {
		// TODO Auto-generated method stub
		return this.spriteBatch;
	}

	@Override
	public Position getGlobalPosFromScreenPos(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTrack(Entity body) {
		// TODO Auto-generated method stub
		this.cameraBody = body;
	}

	@Override
	public void renderTileWorldToFrameBuffer() {
		// TODO Auto-generated method stub
		
	}
}
