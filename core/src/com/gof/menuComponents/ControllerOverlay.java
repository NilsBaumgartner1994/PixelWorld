package com.gof.menuComponents;

import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;
import com.gof.inputs.GamePadType;
import com.gof.inputs.Stick;
import com.gof.simplemenu.SimpleMenuComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;
import com.gof.helper.EasyColor;

public class ControllerOverlay implements SimpleMenuComponent {

	GamePad gamepad;
	TextureRegion leftButton = GlyphAndSymbols.j;
	TextureRegion topButton = GlyphAndSymbols.i;
	TextureRegion rightButton = GlyphAndSymbols.l;
	TextureRegion bottomButton = GlyphAndSymbols.k;

	Sprite leftStickBackgroundSprite;
	Sprite leftStickDirectionSprite;

	public ControllerOverlay(GamePad gamepad) {
		this.gamepad = gamepad;
		update(this.gamepad);
	}

	private void drawButtons(CameraControllerInterface display) {
		float scale = 3f;

		Sprite rightStickBackgroundSprite = new Sprite(
				ResourceLoader.getInstance().getGUI("controlls/leftStick-Background"));
		rightStickBackgroundSprite.setOrigin(0, 0);
		rightStickBackgroundSprite.setScale(scale);
		rightStickBackgroundSprite.setPosition(display.getWidth() - rightStickBackgroundSprite.getWidth() * scale, 0);
		display.drawSprite(rightStickBackgroundSprite);

		ControllerButtonOverlay left = new ControllerButtonOverlay(leftButton, this.gamepad.getButton(GamePadButtons.X),
				EasyColor.BLUELIGHT);
		ControllerButtonOverlay top = new ControllerButtonOverlay(topButton, this.gamepad.getButton(GamePadButtons.Y),
				EasyColor.YELLOWLIGHT);
		ControllerButtonOverlay right = new ControllerButtonOverlay(rightButton,
				this.gamepad.getButton(GamePadButtons.B), EasyColor.REDLIGHT);
		ControllerButtonOverlay bottom = new ControllerButtonOverlay(bottomButton,
				this.gamepad.getButton(GamePadButtons.A), EasyColor.GREENLIGHT);

		float xpos = display.getWidth() - rightStickBackgroundSprite.getWidth() / 2 * scale;
		top.draw(display, xpos,
				(rightStickBackgroundSprite.getHeight() - rightStickBackgroundSprite.getHeight() / 4) * scale, scale);
		left.draw(display, xpos - rightStickBackgroundSprite.getWidth() * scale / 4,
				(rightStickBackgroundSprite.getHeight() / 2) * scale, scale);
		right.draw(display, xpos + rightStickBackgroundSprite.getWidth() * scale / 4,
				(rightStickBackgroundSprite.getHeight() / 2) * scale, scale);
		bottom.draw(display, xpos, rightStickBackgroundSprite.getHeight() / 4 * scale, scale);

	}

	private void drawSticks(CameraControllerInterface display) {

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

	@Override
	public boolean update(GamePad gamepad) {
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

		Stick leftStick = gamepad.getLeftStick();
		leftStickBackgroundSprite = new Sprite(ResourceLoader.getInstance().getGUI("controlls/leftStick-Background"));
		leftStickDirectionSprite = new Sprite(
				ResourceLoader.getInstance().getGUI("controlls/leftStick-" + leftStick.getStickDirection().name()));

		return false;
	}

	@Override
	public int render(CameraControllerInterface display, int ypos) {
		drawSticks(display);
		drawButtons(display);

		return ypos;
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActive(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
