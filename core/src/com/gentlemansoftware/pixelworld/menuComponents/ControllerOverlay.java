package com.gentlemansoftware.pixelworld.menuComponents;

import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.inputs.GamePadButtons;
import com.gentlemansoftware.pixelworld.inputs.GamePadType;
import com.gentlemansoftware.pixelworld.inputs.Stick;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.helper.EasyColor;

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

		ControllerButtonOverlay left = new ControllerButtonOverlay(leftButton,
				this.gamepad.getButton(GamePadButtons.RIGHTPAD_LEFT), EasyColor.BLUELIGHT);
		ControllerButtonOverlay top = new ControllerButtonOverlay(topButton,
				this.gamepad.getButton(GamePadButtons.RIGHTPAD_UP), EasyColor.YELLOWLIGHT);
		ControllerButtonOverlay right = new ControllerButtonOverlay(rightButton,
				this.gamepad.getButton(GamePadButtons.RIGHTPAD_RIGHT), EasyColor.REDLIGHT);
		ControllerButtonOverlay bottom = new ControllerButtonOverlay(bottomButton,
				this.gamepad.getButton(GamePadButtons.RIGHTPAD_DOWN), EasyColor.GREENLIGHT);

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
		leftButton = gamepad.layouttype.getTextureForButton(GamePadButtons.RIGHTPAD_LEFT);
		topButton = gamepad.layouttype.getTextureForButton(GamePadButtons.RIGHTPAD_UP);
		rightButton = gamepad.layouttype.getTextureForButton(GamePadButtons.RIGHTPAD_RIGHT);
		bottomButton = gamepad.layouttype.getTextureForButton(GamePadButtons.RIGHTPAD_DOWN);

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
