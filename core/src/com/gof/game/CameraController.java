package com.gof.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.gof.Inputs.Mouse;
import com.gof.entitys.Entity;
import com.gof.entitys.EntityType;
import com.gof.entitys.LocalPlayer;
import com.gof.materials.Debug;
import com.gof.materials.Material;
import com.gof.materials.MouseMatter;
import com.gof.physics.Body;
import com.gof.physics.Position;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

import items.AbstractItem;
import items.Item;

public class CameraController {

	public Body camera;

	private FrameBuffer fbo;
	private FrameBuffer fbUI;

	private SpriteBatch fboBatch;
	private BitmapFont font;

	private int width;
	public int height;

	public static int zoomLevel = 0;
	public static int zoomLevelmin = -2;
	public static int zoomLevelmax = 3;

	public boolean showInformations = true;

	LocalPlayer player;

	private Entity track;

	public CameraController(LocalPlayer localPlayer, int width, int height) {
		resize(width, height);
		this.player = localPlayer;
		camera.setPosition(0, 0);

		font = new BitmapFont();
		font.setColor(Color.BLACK);
	}

	public void resize(int width, int height) {
		setScreenSize(width, height);
		initCamera();
		initFrameBuffer();
	}

	private void setScreenSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	private void initCamera() {
		camera = new Body();
	}

	private void initFrameBuffer() {
		fbo = new FrameBuffer(Format.RGBA8888, width, height, true);
		fbo.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		fbUI = new FrameBuffer(Format.RGBA8888, width, height, true);
		fbUI.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		if (fboBatch != null)
			fboBatch.dispose();
		fboBatch = new SpriteBatch();
	}

	public void renderToFrameBuffer() {
		fbo.begin();

		Gdx.gl.glViewport(0, 0, fbo.getWidth(), fbo.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		fboBatch.begin();

		if (track != null) {
			camera.setPosition(track);
		}

		int amountToShow = 2;

		int xStart = camera.getPosition().x - amountToShow;
		int yStart = camera.getPosition().y - amountToShow;
		int xEnd = xStart + amountToShow * 2;
		int yEnd = yStart + amountToShow * 2;

		List<MapTile> area = TileWorld.getInstance().getArea(xStart, yStart, xEnd, yEnd);

		area = new ArrayList<MapTile>();

		int xcenter = camera.getPosition().x;
		int ycenter = camera.getPosition().y;

		int safetytiles = 7;

		int breite = this.width / scaleZoom(MapTile.tileWidth) + safetytiles;
		int hoehe = this.height / scaleZoom(MapTile.tileHeight) + safetytiles;

		for (int a = -hoehe + 1; a < hoehe; a++) {
			for (int b = -breite + 1; b < breite; b++) {
				if ((b & 1) != (a & 1))
					continue;
				int x = (a + b) / 2;
				int y = (a - b) / 2;
				area.add(TileWorld.getInstance().getMapTileFromGlobalPos(xcenter + x, ycenter + y));
			}
		}

		Collections.sort(area);
		drawGround(area);

		fboBatch.end();
		fbo.end();
	}

	public int scaleZoom(int orginalPixel) {
		return orginalPixel * numerator / denumerator;
	}

	int numerator = getZoomLevelScaleFactorNumerator();
	int denumerator = getZoomLevelScaleFactorDenumerator();

	private void drawGround(List<MapTile> area) {
		numerator = getZoomLevelScaleFactorNumerator();
		denumerator = getZoomLevelScaleFactorDenumerator();

		int tileWidth = scaleZoom(MapTile.tileWidth);
		int tileHeight = scaleZoom(MapTile.tileHeight);
		int tileWidthHalf = tileWidth / 2;
		int tileHeightHalf = tileHeight / 2;

		Sprite debug = new Sprite(new Debug().getTexture());
		Sprite mouse = new Sprite(new MouseMatter().getTexture());

		List<MapTile> clouds = area.get(area.size() / 2).getMoore();
		for (MapTile cloud : clouds) {
			// cloud.select();
		}
		TileWorld.getInstance().getMapTileFromGlobalPos(camera.getPosition().x, camera.getPosition().y).select();

		Mouse m = Main.getInstance().inputHandler.keyboardHandler.mouse;

		MapTile mouseTile = null;

		if (m != null) {
			Position globalPos = getGlobalPosFromScreenPos(m.getX(), this.height - m.getY());
			mouseTile = TileWorld.getInstance().getMapTileFromGlobalPos(globalPos.x, globalPos.y);
		}

		if (mouseTile != null) {
			mouseTile.select();
		}

		for (MapTile tile : area) {
			Sprite sprite = tile.getMaterialSprite();
			Sprite nature = tile.getNatureTexture();

			Color save = fboBatch.getColor();

			if (tile.isInShaddow()) {
				fboBatch.setColor(save.cpy().add(-0.5f, -0.5f, -0.5f, 0));
			}

			drawTileSprite(sprite, tile.getGlobalX(), tile.getGlobalY(), tileWidthHalf, tileHeightHalf,
					tile.getRotation());
//			drawTileSprite(debug,  tile.getGlobalX(), tile.getGlobalY(), tileWidthHalf, tileHeightHalf,
//						tile.getRotation());

			drawTileSprite(nature, tile.getGlobalX(), tile.getGlobalY(), tileWidthHalf, tileHeightHalf,
					tile.getRotation());

			if (tile.isInShaddow()) {
				fboBatch.setColor(save);
			}

		}

		for (MapTile cloud : clouds) {
			cloud.unselect();
		}

		if (mouseTile != null) {
			mouseTile.unselect();
		}

//		for (MapTile tile : area) {
//			if (((tile.getGlobalX() % 2 == 0 && tile.getGlobalY() % 2 == 0)
//					|| ((tile.getGlobalX() + 1) % 2 == 0 && (tile.getGlobalY() + 1) % 2 == 0))) {
//				drawTileSprite(mouse, tile.getGlobalX(), tile.getGlobalY(), tileWidthHalf, tileHeightHalf,
//						tile.getRotation());
//			}
//		}
	}

	private void drawTileSprite(Sprite sprite, int globalX, int globalY, int tileWidthHalf, int tileHeightHalf,
			int rotation) {
		if (sprite == null) {
			return;
		}
		int x = globalPosToScreenPosX(sprite, globalX, globalY, tileWidthHalf);
		int y = globalPosToScreenPosY(sprite, globalX, globalY, tileHeightHalf);

		sprite.setPosition(x, y);
		sprite.setOrigin(tileWidthHalf, tileHeightHalf);
		sprite.setSize(sprite.getWidth() * numerator / denumerator, sprite.getHeight() * numerator / denumerator);
		sprite.setRotation(rotation);

		drawSprite(sprite);
	}

	private void drawSprite(Sprite sprite) {
		fboBatch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(),
				sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
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

	// SAVE
	private int globalPosToScreenPosX(Sprite sprite, int globalX, int globalY, int tileWidthHalf) {
		int relativeY = (globalY - camera.getPosition().y);
		int relativeX = (globalX - camera.getPosition().x);

		int oldYF = scaleZoom(-camera.getPosition().yFraction);
		int oldXF = scaleZoom(-camera.getPosition().xFraction);

		int spriteCorrection = scaleZoom(-sprite.getRegionWidth() / 2);
		int widthCorrection = this.width / 2;
		int fractionCorrection = oldXF / 2 - oldYF;

		return (relativeX - relativeY) * tileWidthHalf + spriteCorrection + widthCorrection + fractionCorrection;
	}

	private int globalPosToScreenPosY(Sprite sprite, int globalX, int globalY, int tileHeightHalf) {

		int oldY = (globalY - camera.getPosition().y);
		int oldX = (globalX - camera.getPosition().x);

		int oldYF = (-camera.getPosition().yFraction) * numerator / denumerator;
		int oldXF = (-camera.getPosition().xFraction) * numerator / denumerator;

		int relativeY = (globalY - camera.getPosition().y);
		int relativeX = (globalX - camera.getPosition().x);

		int tileCorrection = scaleZoom(-MapTile.tileHeight);
		int heightCorrection = this.height / 2;
		int fractionCorrection = oldXF / 4 + oldYF / 2;

		return (relativeX + relativeY) * tileHeightHalf + tileCorrection + heightCorrection + fractionCorrection;
	}

	public Position getGlobalPosFromScreenPos(int screenX, int screenY) {
		int oldYF = scaleZoom(-camera.getPosition().yFraction);
		int oldXF = scaleZoom(-camera.getPosition().xFraction);

		int fractionCorrectionX = oldXF / 2 - oldYF;
		int fractionCorrectionY = oldXF / 4 + oldYF / 2;

		screenX -= fractionCorrectionX;
		screenY -= fractionCorrectionY;

		screenX -= this.width / 2;
		screenY -= this.height / 2;

		boolean xNegative = screenX < 0 ? true : false;
		boolean yNegative = screenY < 0 ? true : false;

		if (xNegative) {
			screenX *= -1;
		}
		if (yNegative) {
			screenY *= -1;
		}

		Sprite mouse = new Sprite(new MouseMatter().getTexture());

		int spriteCorrection = scaleZoom(mouse.getRegionWidth() / 2);
		screenX += spriteCorrection;

		int regionX = screenX / scaleZoom(mouse.getRegionWidth());
		int regionY = screenY / scaleZoom(mouse.getRegionHeight()) * 2;

		int mouseMapX = screenX % scaleZoom(mouse.getRegionWidth());
		int mouseMapY = screenY % scaleZoom(mouse.getRegionHeight()) *2;

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
		if (showInformations) {
			fbo.begin();

			Gdx.gl.glViewport(0, 0, fbo.getWidth(), fbo.getHeight());

			// nicht clearen
			// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
			// GL20.GL_DEPTH_BUFFER_BIT);

			fboBatch.enableBlending();
			fboBatch.begin();

			// int x = (int) camera.position.x;
			// int y = (int) camera.position.y;

			Position bodyPos = track.getPosition();

			MapTile standOn = TileWorld.getInstance().getMapTileFromGlobalPos((int) bodyPos.x, (int) bodyPos.y);

			line = 1;
			drawInformationLine("FPS: " + Gdx.graphics.getFramesPerSecond());

			if (track.getEntityType() == EntityType.PLAYER) {
				LocalPlayer p = (LocalPlayer) track;
				drawInformationLine("Player: " + Main.getInstance().playerHandler.getPlayerNumber(p));
			}
			drawInformationLine("Zoom: " + zoomLevel);
			drawInformationLine("Body Chunk: " + standOn.chunk.x + "|" + standOn.chunk.y);
			drawInformationLine("Body Position: " + bodyPos.x + ":" + bodyPos.xFraction + "|" + bodyPos.y + ":"
					+ bodyPos.yFraction);

			Mouse m = Main.getInstance().inputHandler.keyboardHandler.mouse;

			if (m != null) {

			}

			drawInformationLine("Stand On: " + standOn.material.texture);
			if (standOn.nature != null) {
				drawInformationLine("Nature: " + standOn.nature.texture);
			}
			// drawInformationLine("Dir: " +
			// track.body.getLinearVelocity().toString());

			fboBatch.end();
			fbo.end();
		}
	}

	private void drawInformationLine(String s) {
		float z = font.getLineHeight();
		font.draw(fboBatch, s, 10, height - line * z);
		line++;
	}

	public void drawIconBar() {
		Sprite framebar = new Sprite(ResourceLoader.getInstance().getIcon("framebar"));
		framebar.setPosition(this.width / 2 - framebar.getRegionWidth() / 2, 10);
		drawSprite(framebar);

		Sprite iconFrame = new Sprite(ResourceLoader.getInstance().getIcon("frame"));
		Sprite activIconFrame = new Sprite(ResourceLoader.getInstance().getIcon("frame_activ"));

		int invPos = 0;
		for (int i = -4; i < 4; i++) {
			Sprite drawFrame = player.inventory.isActivSlot(invPos) ? activIconFrame : iconFrame;

			int xPos = this.width / 2 + (i * iconFrame.getRegionWidth() + i * 10 + 5);
			int yPos = 10 + framebar.getRegionHeight() / 2 - activIconFrame.getRegionHeight() / 2;

			drawFrame.setPosition(xPos, yPos);
			drawSprite(drawFrame);

			AbstractItem item = player.inventory.getItem(invPos);
			if (item != null) {
				Sprite itemIcon = new Sprite(item.getTexture());
				itemIcon.setPosition(xPos, yPos);
				drawSprite(itemIcon);
			}

			invPos++;
		}
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

		drawIconBar();

		Mouse m = Main.getInstance().inputHandler.keyboardHandler.mouse;

		Sprite hand = new Sprite(ResourceLoader.getInstance().getUI("hand_select"));

		if (m != null) {
			hand.setPosition(m.getX() - hand.getRegionWidth() / 2,
					this.height - m.getY() - hand.getRegionHeight() / 2);
			fboBatch.draw(hand, hand.getX(), hand.getY(), hand.getOriginX(), hand.getOriginY(), hand.getWidth(),
					hand.getHeight(), hand.getScaleX(), hand.getScaleY(), hand.getRotation());
		}

		fboBatch.end();
		fbUI.end();
	}

	public void renderToScreen() {
		fboBatch.begin();
		fboBatch.draw(fbo.getColorBufferTexture(), 0, 0, width, height, 0, 0, 1, 1);
		fboBatch.draw(fbUI.getColorBufferTexture(), 0, 0, width, height, 0, 0, 1, 1);
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
		zoomLevel += amount;
		if (zoomLevel < zoomLevelmin)
			zoomLevel = zoomLevelmin;
		if (zoomLevel > zoomLevelmax)
			zoomLevel = zoomLevelmax;
	}

	public void setTrack(Entity body) {
		this.track = body;
	}
}
