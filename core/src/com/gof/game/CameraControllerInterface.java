package com.gof.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gof.entitys.Entity;
import com.gof.physics.Direction;
import com.gof.physics.Position;

public interface CameraControllerInterface {

	public Direction getCameraDirection();
	public void resize(int width, int height);
	public void render();
	public void rotateCamera(float deg);
	public void renderTileWorldToFrameBuffer();
	public int scaleZoom(int orginalPixel);
	public void drawSprite(Sprite sprite);
	public void drawSprite(Sprite sprite, Color color);
	public Position getGlobalPosFromScreenPos(int screenX, int screenY);
	public void renderToInformationBuffer();
	public void renderGUI();
	public void drawMenu();
	public int drawSpriteAndSubtractYpos(Sprite s, int xpos, int ypos);
	public void renderToScreen();
	public void distanceIncrease();
	public void distanceDecrease();
	public void changeDistance(int amount);
	public void dispose();
	public void setTrack(Entity body);
	public int getWidth();
	public int getHeigth();
	public BitmapFont getFont();
	public GlyphLayout getLayout();
	public SpriteBatch getSpriteBatch();
}
