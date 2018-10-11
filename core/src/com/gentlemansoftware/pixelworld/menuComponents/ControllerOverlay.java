package com.gentlemansoftware.pixelworld.menuComponents;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.inputs.Button;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.inputs.GamePadButtons;
import com.gentlemansoftware.pixelworld.inputs.Stick;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;

public class ControllerOverlay implements SimpleMenuComponent {

	GamePad gamepad;

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

		ControllerButtonOverlay left = this.gamepad.layouttype.getButton(GamePadButtons.RIGHTPAD_LEFT);
		Button leftButton = this.gamepad.getButton(GamePadButtons.RIGHTPAD_LEFT);

		ControllerButtonOverlay top = this.gamepad.layouttype.getButton(GamePadButtons.RIGHTPAD_UP);
		Button topButton = this.gamepad.getButton(GamePadButtons.RIGHTPAD_UP);

		ControllerButtonOverlay down = this.gamepad.layouttype.getButton(GamePadButtons.RIGHTPAD_DOWN);
		Button downButton = this.gamepad.getButton(GamePadButtons.RIGHTPAD_DOWN);

		ControllerButtonOverlay right = this.gamepad.layouttype.getButton(GamePadButtons.RIGHTPAD_RIGHT);
		Button rightButton = this.gamepad.getButton(GamePadButtons.RIGHTPAD_RIGHT);

		float rsbsWidth = rightStickBackgroundSprite.getWidth();
		float rsbsHeight = rightStickBackgroundSprite.getHeight();

		float xpos = display.getWidth() - rsbsWidth / 2 * scale;
		top.draw(display, xpos, (rsbsHeight - rsbsHeight / 4) * scale, scale, topButton);
		left.draw(display, xpos - rsbsWidth * scale / 4, (rsbsHeight / 2) * scale, scale, leftButton);
		right.draw(display, xpos + rsbsWidth * scale / 4, (rsbsHeight / 2) * scale, scale, rightButton);
		down.draw(display, xpos, rsbsHeight / 4 * scale, scale, downButton);

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
		System.out.println(gamepad.layouttype.getClass().getName());

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
