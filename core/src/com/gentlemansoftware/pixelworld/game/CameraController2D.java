package com.gentlemansoftware.pixelworld.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.gentlemansoftware.pixelworld.entitys.EasyDrawableInterface;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.helper.MemoryHelper;
import com.gentlemansoftware.pixelworld.helper.SplitScreenDimension;
import com.gentlemansoftware.pixelworld.inputs.Mouse;
import com.gentlemansoftware.pixelworld.materials.MyMaterial;
import com.gentlemansoftware.pixelworld.physics.Body;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.EasyDrawableInterfaceComperator;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.physics.PositionComperator;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.shaders.ShaddowShader;
import com.gentlemansoftware.pixelworld.sound.EasySounds;
import com.gentlemansoftware.pixelworld.world.Block;
import com.gentlemansoftware.pixelworld.world.Chunk;
import com.gentlemansoftware.pixelworld.world.MapTile;
import com.gentlemansoftware.pixelworld.world.TileWorld;

public class CameraController2D implements CameraControllerInterface {

	public Body camera;

	private FrameBuffer fbo;
	private FrameBuffer fbUI;

	public SpriteBatch fboBatch;
	public BitmapFont font;

	public SplitScreenDimension dimension;

	public static int zoomLevel = 0;
	public static int zoomLevelmin = -2;
	public static int zoomLevelmax = 3;

	Direction cameraDirection = Direction.NORTH;

	public GlyphLayout layout;

	public Entity track;

	private User user;

	public CameraController2D(User localUser, SplitScreenDimension dimension) {
		resize(dimension);
		this.user = localUser;
		camera.setPositionForce(0, 0);
		initFont();

		layout = new GlyphLayout();
	}

	public void initFont() {
		font = new BitmapFont();
		// FileHandle f = Gdx.files.internal("./data/fonts/NicerNightie.ttf");
		// FreeTypeFontGenerator generator = new FreeTypeFontGenerator(f);
		// FreeTypeFontParameter param = new FreeTypeFontParameter();
		// param.size = 15;
		//
		// setFont(generator.generateFont(param));
		// System.out.println("Font Null: " + font == null);
		//
		// generator.dispose();

		font.setColor(Color.BLACK);
	}

	public void setFont(BitmapFont f) {
		this.font = f;
	}

	public Position getCameraPosition() {
		return this.camera.getPosition().cpy();
	}

	public void setCameraPosition(Position pos) {
		this.camera.setPositionForce(pos);
	}

	public void resize(SplitScreenDimension dimension) {
		setScreenSize(dimension);
		initCamera();
		initFrameBuffer();
	}

	private void setScreenSize(SplitScreenDimension dimension) {
		this.dimension = dimension;
	}

	private void initCamera() {
		camera = new Body();
	}

	private void initFrameBuffer() {
		if (fbo != null) {
			fbo.dispose();
		}
		fbo = new FrameBuffer(Format.RGBA8888, getWidth(), getHeight(), true);
		fbo.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		if (fbUI != null) {
			fbUI.dispose();
		}
		fbUI = new FrameBuffer(Format.RGBA8888, getWidth(), getHeight(), true);
		fbUI.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		if (fboBatch != null)
			fboBatch.dispose();
		fboBatch = new SpriteBatch();
	}

	public void renderTileWorldToFrameBuffer() {
		fbo.begin();

		Gdx.gl.glViewport(0, 0, fbo.getWidth(), fbo.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		fboBatch.begin();

		boolean gameActive = this.user.menuHandler.activMenu == this.user.menuHandler.ingameMenu;
		Color save = fboBatch.getColor().cpy();

		if (!gameActive) {
			float intensivity = 0.8f;
			Color shaddow = new Color(new Color(intensivity, intensivity, intensivity, 1));
			fboBatch.setColor(shaddow);
		}

		TileWorld world = user.getTileWorld();
		if (world != null) {
			world.deactivateAllChunks();
			Chunk playerChunk = world.getChunkGlobalPos(camera.getPosition().x, camera.getPosition().y);
			world.activateChunk(playerChunk);
			world.activateChunk(playerChunk.getMoore());
			List<EasyDrawableInterface> area = getAreaToDraw(world);
			
			Position cursorPosOnWorld = getGlobalPosFromScreenPos((int) this.user.gamepad.getCursor().pos.x, this.getHeight() - (int) this.user.gamepad.getCursor().pos.y);
			MapTile t = world.getMapTileFromGlobalPos(cursorPosOnWorld.x, cursorPosOnWorld.y);
			Block selection = new Block(t,MyMaterial.SELECTION);
			area.add(selection);
			
			Collections.sort(area, new EasyDrawableInterfaceComperator(this.cameraDirection));
			// drawNatureShaddow(area, world);
			drawNatureAndEntitys(area, world);
		}

		fboBatch.setColor(save);

		fboBatch.end();
		fbo.end();
	}

	public Direction getCameraDirection() {
		return this.cameraDirection;
	}

	public List<EasyDrawableInterface> getAreaToDraw(TileWorld world) {
		if (track != null) {
			camera.setPositionForce(track);
		}

		List<EasyDrawableInterface> entitys = new ArrayList<EasyDrawableInterface>();

		int xcenter = camera.getPosition().x;
		int ycenter = camera.getPosition().y;

		// int safetytiles = 7;
		int safetytiles = 7;

		int breite = this.getWidth() / scaleZoom(MapTile.tileWidth) + safetytiles;
		int hoehe = this.getHeight() / scaleZoom(MapTile.tileHeight) + safetytiles;

		for (int a = -hoehe + 1; a < hoehe; a++) { // get a diamond shape
			for (int b = -breite + 1; b < breite; b++) {
				if ((b & 1) != (a & 1))
					continue;
				int x = (a + b) / 2;
				int y = (a - b) / 2;
				MapTile m = world.getMapTileFromGlobalPos(xcenter + x, ycenter + y);
				if (m != null) {
					if (m.b != null) {
						entitys.add(m.b);
					}
					entitys.addAll(m.e);
				}
			}
		}

		numerator = getZoomLevelScaleFactorNumerator();
		denumerator = getZoomLevelScaleFactorDenumerator();

		return entitys;
	}

	public int scaleZoom(int orginalPixel) {
		return orginalPixel * numerator / denumerator;
	}

	public float scaleZoom(float orginalPixel) {
		return orginalPixel * numerator / denumerator;
	}

	int numerator = getZoomLevelScaleFactorNumerator();
	int denumerator = getZoomLevelScaleFactorDenumerator();

	private void drawNatureShaddow(List<MapTile> area, TileWorld world) {
		int tileWidth = scaleZoom(MapTile.tileWidth);
		int tileHeight = scaleZoom(MapTile.tileHeight);
		int tileWidthHalf = tileWidth / 2;
		int tileHeightHalf = tileHeight / 2;

		int shaddowRotation = world.time.getShaddowAngle();

		fboBatch.setShader(new ShaddowShader());
		Color save = fboBatch.getColor().cpy();

		float intense = world.time.getLightIntense();
		float shaddowLength = world.time.getShaddowLength();
		Color shaddow = new Color(new Color(0, 0, 0, intense));
		fboBatch.setColor(shaddow);

		for (MapTile tile : area) {
			// Sprite nature = tile.getNatureTexture();
			// if (nature != null) {
			//
			// nature.setScale(1, shaddowLength);
			// drawTileSprite(nature, tile.getGlobalPosition(), tileWidthHalf,
			// tileHeightHalf, shaddowRotation);
			// }
		}

		fboBatch.setShader(null);
		fboBatch.setColor(save);
	}

	private void drawNatureAndEntitys(List<EasyDrawableInterface> entitys, TileWorld world) {
		int tileWidth = scaleZoom(MapTile.tileWidth);
		int tileHeight = scaleZoom(MapTile.tileHeight);
		int tileWidthHalf = tileWidth / 2;
		int tileHeightHalf = tileHeight / 2;

		drawOrderNumber = 0;
		for (EasyDrawableInterface d : entitys) {
			if (d instanceof Entity) {
				Entity e = (Entity) d;
				if (e.world == null) {
					e.setTransient(world);
				}
				e.playSoundForUser(getCameraPosition(), this.user);
			}
			Sprite s = d.getSprite(this.cameraDirection);
			drawTileSprite(s, d.getPosition(), tileWidthHalf, tileHeightHalf, 0);
			drawOrderNumber++;
		}
	}

	int drawOrderNumber = 0;

	private void drawTileSprite(Sprite sprite, Position globalPos, int tileWidthHalf, int tileHeightHalf,
			int rotation) {
		if (sprite == null) {
			return;
		}
		int[] xy = globalPosToScreenPos(globalPos, tileHeightHalf, tileWidthHalf, sprite);

		sprite.setPosition(xy[0], xy[1] + scaleZoom(globalPos.zFraction));

		// sprite.setOrigin(tileWidthHalf, tileHeightHalf);
		sprite.setOrigin(scaleZoom(90), scaleZoom(128 - MapTile.tileHeight / 2));
		sprite.setSize(scaleZoom(sprite.getWidth()), scaleZoom(sprite.getHeight()));
		sprite.setRotation(rotation);

		drawSprite(sprite);
		if (this.user.profile.debugProfile.showCoordinatesOnMapTiles.getVar()) {
			drawInformationCenteredAtPos(xy[0] + scaleZoom(sprite.getRegionWidth() / 2),
					xy[1] + tileHeightHalf * 3 + tileHeightHalf / 2, globalPos.x + "/" + globalPos.y);
		}
		if (this.user.profile.debugProfile.showMapTilesDrawOrder.getVar()) {
			PositionComperator comp = new PositionComperator(this.cameraDirection);
			drawInformationCenteredAtPos(xy[0] + scaleZoom(sprite.getRegionWidth() / 2),
					xy[1] + tileHeightHalf * 5 + scaleZoom(globalPos.zFraction),
					"" + drawOrderNumber + "\n" + comp.heightCompareLength(globalPos));
		}

	}

	public void drawSprite(Sprite sprite) {

		fboBatch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(),
				sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
	}

	public void drawSprite(Sprite sprite, Color color) {
		Color copy = fboBatch.getColor().cpy();
		fboBatch.setColor(color);
		drawSprite(sprite);
		fboBatch.setColor(copy);
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

	private int[] globalPosToScreenPos(Position globalPos, int tileHeightHalf, int tileWidthHalf, Sprite sprite) {
		int[] screenPos = new int[2];

		int globalY = globalPos.y;
		int globalX = globalPos.x;
		int globalYFrac = globalPos.yFraction;
		int globalXFrac = globalPos.xFraction;

		int relativeY = (globalY - camera.getPosition().y);
		int relativeX = (globalX - camera.getPosition().x);

		int oldYF = scaleZoom(globalYFrac - camera.getPosition().yFraction);
		int oldXF = scaleZoom(globalXFrac - camera.getPosition().xFraction);

		int spriteCorrection = scaleZoom(-sprite.getRegionWidth() / 2);
		int widthCorrection = this.getWidth() / 2;
		int tileCorrection = scaleZoom(-MapTile.tileHeight);
		int heightCorrection = this.getHeight() / 2;

		int xPosMultXPart = 1;
		int xPosMultYPart = -1;
		int yPosMultXPart = 1;
		int yPosMultYPart = 1;

		// int extraXCorrection = 0;
		// int extraYCorrection = 0;

		if (this.cameraDirection == Direction.NORTH) {
			xPosMultXPart = 1;
			xPosMultYPart = -1;
			yPosMultXPart = 1;
			yPosMultYPart = 1;
		}
		if (this.cameraDirection == Direction.EAST) {
			xPosMultXPart = -1;
			xPosMultYPart = -1;
			yPosMultXPart = 1;
			yPosMultYPart = -1;
		}
		if (this.cameraDirection == Direction.SOUTH) {
			xPosMultXPart = -1;
			xPosMultYPart = 1;
			yPosMultXPart = -1;
			yPosMultYPart = -1;
		}
		if (this.cameraDirection == Direction.WEST) {
			xPosMultXPart = 1;
			xPosMultYPart = 1;
			yPosMultXPart = -1;
			yPosMultYPart = 1;
		}

		// relativeX += extraXCorrection;
		// relativeY += extraYCorrection;

		int relativeXYForX = (xPosMultXPart * relativeX + xPosMultYPart * relativeY);
		int fractionCorrectionX = (xPosMultXPart * oldXF / 2 + xPosMultYPart * oldYF);
		screenPos[0] = relativeXYForX * tileWidthHalf + spriteCorrection + widthCorrection + fractionCorrectionX;

		int relativeXYForY = (yPosMultXPart * relativeX + yPosMultYPart * relativeY);
		int fractionCorrectionY = (yPosMultXPart * oldXF / 4 + yPosMultYPart * oldYF / 2);
		screenPos[1] = relativeXYForY * tileHeightHalf + tileCorrection + heightCorrection + fractionCorrectionY;

		return screenPos;
	}

	public Position getGlobalPosFromScreenPos(int screenX, int screenY) {
		int oldYF = scaleZoom(-camera.getPosition().yFraction);
		int oldXF = scaleZoom(-camera.getPosition().xFraction);

		int fractionCorrectionX = oldXF / 2 - oldYF;
		int fractionCorrectionY = oldXF / 4 + oldYF / 2;

		screenX -= fractionCorrectionX;
		screenY -= fractionCorrectionY;

		screenX -= this.getWidth() / 2;
		screenY -= this.getHeight() / 2;

		boolean xNegative = screenX < 0 ? true : false;
		boolean yNegative = screenY < 0 ? true : false;

		if (xNegative) {
			screenX *= -1;
		}
		if (yNegative) {
			screenY *= -1;
		}

		Sprite mouse = new Sprite(ResourceLoader.getInstance().getTile("mouseMatter"));

		int spriteCorrection = scaleZoom(mouse.getRegionWidth() / 2);
		screenX += spriteCorrection;

		int regionX = screenX / scaleZoom(mouse.getRegionWidth());
		int regionY = screenY / scaleZoom(mouse.getRegionHeight()) * 2;

		int mouseMapX = screenX % scaleZoom(mouse.getRegionWidth());
		int mouseMapY = screenY % scaleZoom(mouse.getRegionHeight()) * 2;

		if (xNegative) {
			regionX *= -1;
		}
		if (yNegative) {
			regionY = -1 * regionY;
		}

		if (yNegative) {
			regionY -= 2;
		}

		int[] region = getRegionDXFromMouseMap(mouseMapX, mouseMapY, scaleZoom(mouse.getRegionWidth()),
				scaleZoom(mouse.getRegionHeight() * 2), scaleZoom(mouse.getRegionWidth() / 2), xNegative, yNegative);

		if (xNegative) {
			// region[0]*=-1;
			// region[1]*=-1;
		}
		if (yNegative) {
		}

		int xCorrection = regionX + regionY / 2 + region[0];
		int yCorrection = -regionX + regionY / 2 + region[1];

		if (this.cameraDirection == Direction.NORTH) {

		}
		if (this.cameraDirection == Direction.EAST) {
			int helper = xCorrection;
			xCorrection = yCorrection;
			yCorrection = -helper + 1;

		}
		if (this.cameraDirection == Direction.SOUTH) {
			xCorrection = -xCorrection + 1;
			yCorrection = -yCorrection + 1;
		}
		if (this.cameraDirection == Direction.WEST) {
			int helper = xCorrection;
			xCorrection = -yCorrection + 1;
			yCorrection = helper;
		}

		int globalX = camera.getPosition().x + xCorrection;
		int globalY = camera.getPosition().y + yCorrection;

		Position globalPos = new Position(globalX, globalY);

		return globalPos;
	}

	/**
	 * Return int[dx,dy]
	 * 
	 * @param mouseMapX
	 * @param mouseMapY
	 * @param mouseMapXMax
	 * @param mouseMapYMax
	 * @param maxDistance
	 * @return
	 */
	private int[] getRegionDXFromMouseMap(int mouseMapX, int mouseMapY, int mouseMapXMax, int mouseMapYMax,
			int maxDistance, boolean xNegative, boolean yNegative) {
		int[] back = { 0, 0 };

		int yTopD = (mouseMapY - mouseMapYMax) * -1;
		int xRightD = (mouseMapX - mouseMapXMax) * -1;

		/*
		 * https://www.gamedev.net/resources/_/technical/game-programming/
		 * isometric-n-hexagonal-maps-part-i-r747
		 */

		if (mouseMapX + mouseMapY <= maxDistance) { /* Gr�ner Bereich */

			if (xNegative && yNegative) {
				back[0] = 1;
			} else if (xNegative) {
				back[1] = -1;
			} else if (yNegative) {
				back[1] = 1;
			} else {
				back[0] = -1;
			}
		} else if (xRightD + mouseMapY <= maxDistance) { /* Blauer Bereich */
			if (xNegative && yNegative) {
				back[1] = 1;
			} else if (xNegative) {
				back[0] = -1;
			} else if (yNegative) {
				back[0] = 1;
			} else {
				back[1] = -1;
			}
		}

		else if (mouseMapX + yTopD <= maxDistance) { /* Roter Bereich */

			if (xNegative && yNegative) {
				back[1] = -1;
			} else if (xNegative) {
				back[0] = 1;
			} else if (yNegative) {
				back[0] = -1;
			} else {
				back[1] = 1;
			}
		} else if (xRightD + yTopD <= maxDistance) { /* Gelber Bereich */
			if (xNegative && yNegative) { /* Hier Gr�ner */
				back[0] = -1;
			} else /* ende Gr�ner Bereich */
			if (xNegative) {
				back[1] = 1;
			} else if (yNegative) {
				back[1] = -1;
			} else {
				back[0] = 1;
			}
		} else {
			// Main.log(getClass(), "Drin");
		}

		return back;
	}

	static int line;

	public void renderToInformationBuffer() {
		fbo.begin();

		Gdx.gl.glViewport(0, 0, fbo.getWidth(), fbo.getHeight());

		// nicht clearen
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
		// GL20.GL_DEPTH_BUFFER_BIT);

		fboBatch.enableBlending();
		fboBatch.begin();

		// int x = (int) camera.position.x;
		// int y = (int) camera.position.y;

		line = 1;
		font.setColor(Color.YELLOW);
		drawInformationLine("FPS: " + Gdx.graphics.getFramesPerSecond());
		
		drawInformationLine("Min Memory: " + MemoryHelper.getMinMemory());
		drawInformationLine("Max Memory: " + MemoryHelper.getMaxMemory());
		drawInformationLine("Avg Memory: " + MemoryHelper.getAvgMemory());
		drawInformationLine("Player: " + Main.getInstance().userHandler.getUserNumber(this.user));
		drawInformationLine("GamePad: " + this.user.gamepad.toString());
		drawInformationLine("Zoom: " + zoomLevel);
		drawInformationLine("CameraDirection: " + this.cameraDirection);

		TileWorld world = this.user.getTileWorld();
		if (world != null) {
			drawTileWorldInformations(world);
		}

		Mouse m = Main.getInstance().inputHandler.keyboardHandler.mouse;

		if (m != null) {
			drawInformationLine("Mouse: "
					+ getGlobalPosFromScreenPos((int) m.getPos().x, this.getHeight() - (int) m.getPos().y).toString());
		}
		
		drawInformationLine("Cursor: "
				+ getGlobalPosFromScreenPos((int) this.user.gamepad.getCursor().pos.x, this.getHeight() - (int) this.user.gamepad.getCursor().pos.y).toString());
		
		font.setColor(Color.BLACK);

		fboBatch.end();
		fbo.end();
	}

	private void drawTileWorldInformations(TileWorld world) {
		float light = world.time.getLightIntense();
		drawInformationLine(
				"Days: " + world.time.getDays() + " - Time: " + world.time.getTicks() + " - Light: " + light);
		drawInformationLine("ShaddowAngle: " + world.time.getShaddowAngle());

		if (track != null) {
			Position bodyPos = track.getPosition();
			drawInformationLine("Body Position: " + bodyPos.toString());
			MapTile standOn = world.getMapTileFromGlobalPos((int) bodyPos.x, (int) bodyPos.y);
			drawInformationLine("Body Chunk: " + standOn.chunk.x + "|" + standOn.chunk.y);
			// drawInformationLine("Stand On: " + standOn.material.getName());
			// drawInformationLine("MapTile Height: " + standOn.height);
			// if (standOn.nature != null) {
			// drawInformationLine("Nature: " + standOn.nature.getName());
			// }
		}
	}

	private void drawInformationLine(String s) {
		float z = font.getLineHeight();
		drawInformationLeftAlignedAtPos(10, getHeight() - line * z, s);
		line++;
	}

	public void drawInformationLeftAlignedAtPos(float x, float y, String s) {
		float z = font.getLineHeight();
		font.draw(fboBatch, s, x, y - 0.5f * z);
	}

	public void drawInformationCenteredAtPos(float x, float y, String s) {
		getLayout().setText(getFont(), s);
		int stringWidth = (int) getLayout().width;
		int stringHeight = (int) getLayout().height;
		font.draw(fboBatch, s, x - stringWidth / 2, y - stringHeight / 2);
	}

	public void drawInformationRightAlignedAtPos(float x, float y, String s) {
		getLayout().setText(getFont(), s);
		int stringWidth = (int) getLayout().width;
		drawInformationLeftAlignedAtPos(x - stringWidth, y, s);
	}

	public void renderGUI() {
		fbUI.begin();

		Gdx.gl.glViewport(0, 0, fbUI.getWidth(), fbUI.getHeight());

		// nicht clearen

		fboBatch.enableBlending();
		fboBatch.begin();

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
		// GL20.GL_DEPTH_BUFFER_BIT);

		drawMenu();

		fboBatch.end();
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

	public void renderToScreen() {
		fboBatch.begin();
		fboBatch.draw(fbo.getColorBufferTexture(), dimension.x, dimension.y, getWidth(), getHeight(), 0, 0, 1, 1);
		fboBatch.draw(fbUI.getColorBufferTexture(), dimension.x, dimension.y, getWidth(), getHeight(), 0, 0, 1, 1);
		fboBatch.end();
	}

	public void distanceIncrease() {
		changeDistance(1);
	}

	public void distanceDecrease() {
		changeDistance(-1);
	}

	public void dispose() {
		fbo.dispose();
		fboBatch.dispose();
	}

	public void changeDistance(int amount) {
		this.user.soundManager.playSound(EasySounds.CLICK, 1f);

		zoomLevel += amount;
		if (zoomLevel < zoomLevelmin)
			zoomLevel = zoomLevelmin;
		if (zoomLevel > zoomLevelmax)
			zoomLevel = zoomLevelmax;
	}

	public void setTrack(Entity body) {
		this.track = body;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		renderTileWorldToFrameBuffer();
		if (this.user.profile.debugProfile.showDebugInformationSide.value) {
			renderToInformationBuffer();
		}
		renderGUI();
		renderToScreen();
	}

	@Override
	public void rotateCamera(float deg) {
		// TODO Auto-generated method stub
		this.cameraDirection = Direction.rotateDirection(cameraDirection, 90);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.dimension.width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.dimension.height;
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
		return this.fboBatch;
	}

}