package com.gof.menuComponents;

import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;
import com.gof.inputs.GamePadType;
import com.gof.inputs.Stick;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;

public class ControllerOverlay {

	GamePad gamepad;

	public ControllerOverlay(GamePad gamepad) {
		this.gamepad = gamepad;
	}

	public void draw(CameraControllerInterface display) {
		drawSticks(display);
		drawButtons(display);
	}

	private void drawButtons(CameraControllerInterface display) {
		TextureRegion leftButton = GlyphAndSymbols.j;
		TextureRegion topButton = GlyphAndSymbols.i;
		TextureRegion rightButton = GlyphAndSymbols.l;
		TextureRegion bottomButton = GlyphAndSymbols.k;

		if (gamepad.layouttype == GamePadType.KEYBOARD) {
			leftButton = GlyphAndSymbols.j;
			topButton = GlyphAndSymbols.i;
			rightButton = GlyphAndSymbols.l;
			bottomButton = GlyphAndSymbols.k;
		}
		if (gamepad.layouttype == GamePadType.PLAYSTATION) {
			leftButton = GlyphAndSymbols.XboxX;
			topButton = GlyphAndSymbols.XboxY;
			rightButton = GlyphAndSymbols.XboxB;
			bottomButton = GlyphAndSymbols.XboxA;
		}
		if (gamepad.layouttype == GamePadType.XBOX) {
			leftButton = GlyphAndSymbols.PlaystationSquare;
			topButton = GlyphAndSymbols.PlaystationTriangle;
			rightButton = GlyphAndSymbols.PlaystationCircle;
			bottomButton = GlyphAndSymbols.PlaystationX;
		}

		float scale = 3f;

		Sprite rightStickBackgroundSprite = new Sprite(
				ResourceLoader.getInstance().getGUI("controlls/leftStick-Background"));
		rightStickBackgroundSprite.setOrigin(0, 0);
		rightStickBackgroundSprite.setScale(scale);
		rightStickBackgroundSprite.setPosition(display.getWidth() - rightStickBackgroundSprite.getWidth() * scale, 0);
		display.drawSprite(rightStickBackgroundSprite);
		
		Color topColor = new Color(154/255f,235/255f,0,255/255f); //Hell Gruen
		ControllerButtonOverlay left = new ControllerButtonOverlay(leftButton,this.gamepad.getButton(GamePadButtons.A),topColor);
		ControllerButtonOverlay top = new ControllerButtonOverlay(topButton,this.gamepad.getButton(GamePadButtons.B),topColor);
		ControllerButtonOverlay right = new ControllerButtonOverlay(rightButton,this.gamepad.getButton(GamePadButtons.A),topColor);
		ControllerButtonOverlay bottom = new ControllerButtonOverlay(bottomButton,this.gamepad.getButton(GamePadButtons.A),topColor);
		
		
		float xpos = display.getWidth() - rightStickBackgroundSprite.getWidth()/2 * scale;
		left.draw(display, xpos, rightStickBackgroundSprite.getHeight()/2*scale, scale);
		
	}

	private void drawSticks(CameraControllerInterface display) {
		Stick leftStick = gamepad.getLeftStick();
		Sprite leftStickBackgroundSprite = new Sprite(
				ResourceLoader.getInstance().getGUI("controlls/leftStick-Background"));
		Sprite leftStickDirectionSprite = new Sprite(
				ResourceLoader.getInstance().getGUI("controlls/leftStick-" + leftStick.getStickDirection().name()));

		float scale = 3f;

		leftStickBackgroundSprite.setOrigin(0, 0);
		leftStickBackgroundSprite.setScale(scale);
		leftStickBackgroundSprite.setPosition(0, 0);
		display.drawSprite(leftStickBackgroundSprite);

		leftStickDirectionSprite.setOrigin(0, 0);
		leftStickDirectionSprite.setScale(scale);
		leftStickDirectionSprite.setPosition(0, 0);
		display.drawSprite(leftStickDirectionSprite);
	}

}
