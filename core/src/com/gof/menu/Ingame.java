package com.gof.menu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;
import com.gof.inputs.Stick;
import com.gof.entitys.Human;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;
import com.gof.profiles.User;
import com.gof.sound.EasySounds;
import com.gof.sound.SoundManager;
import com.gof.items.AbstractItem;
import com.gof.menuComponents.ControllerOverlay;
import com.gof.physics.Direction;

public class Ingame extends SimpleMenu {

	private ControllerOverlay controlleroverlay;

	public Ingame(MenuHandler menuHandler, Menu parent) {
		super(menuHandler, parent, "Back", null);
		controlleroverlay = new ControllerOverlay(menuHandler.user.gamepad);
	}

	@Override
	public void render(CameraControllerInterface display) {
		super.render(display);
		controlleroverlay.draw(display);
		drawIconBar(display);
	}

	@Override
	public boolean update(GamePad gamepad) {
		if (gamepad.isButtonTyped(GamePadButtons.ESC)) {
			this.handler.setActivMenu(this.handler.pauseMenu);
		}
		User user = this.handler.user;
		if (gamepad.isButtonTyped(GamePadButtons.DOWN)) {
			user.cameraController.changeDistance(1);
		}
		if (gamepad.isButtonTyped(GamePadButtons.UP)) {
			user.cameraController.changeDistance(-1);
		}

		Human human = user.human;
		switch (this.handler.user.cameraController.getCameraDirection()) {
		case NORTH:
			human.setLeftStick(gamepad.getLeftStick().getVec());
			break;
		case EAST:
			human.setLeftStick(gamepad.getLeftStick().getVec().rotate90(2).scl(-1));
			break;
		case SOUTH:
			human.setLeftStick(gamepad.getLeftStick().getVec().scl(-1));
			break;
		case WEST:
			human.setLeftStick(gamepad.getLeftStick().getVec().rotate90(3));
			break;
		default:
			break;
		}

		if (gamepad.isButtonTyped(GamePadButtons.R2)) {
			user.cameraController.rotateCamera(10);
			// human.use(human.getNextBlockInDirection());
		}
		human.run(gamepad.isButtonPressed(GamePadButtons.SHIFT));
		if (gamepad.isButtonTyped(GamePadButtons.LEFT)) {
			human.inventory.setActivSlot(human.inventory.getActivSlot() - 1);
		}
		if (gamepad.isButtonTyped(GamePadButtons.RIGHT)) {
			human.inventory.setActivSlot(human.inventory.getActivSlot() + 1);
		}

		return true;
	}

	private void drawIconBar(CameraControllerInterface display) {

		Sprite framebar = new Sprite(ResourceLoader.getInstance().getIcon("framebar"));
		framebar.setPosition(display.getWidth() / 2 - framebar.getRegionWidth() / 2, 10);
		display.drawSprite(framebar);

		Sprite iconFrame = new Sprite(ResourceLoader.getInstance().getIcon("frame"));
		Sprite activIconFrame = new Sprite(ResourceLoader.getInstance().getIcon("frame_activ"));

		Human human = this.handler.user.human;
		int invPos = 0;
		for (int i = -4; i < 4; i++) {
			Sprite drawFrame = human.inventory.isActivSlot(invPos) ? activIconFrame : iconFrame;

			int xPos = display.getWidth() / 2 + (i * iconFrame.getRegionWidth() + i * 10 + 5);
			int yPos = 10 + framebar.getRegionHeight() / 2 - activIconFrame.getRegionHeight() / 2;

			drawFrame.setPosition(xPos, yPos);
			display.drawSprite(drawFrame);

			AbstractItem item = human.inventory.getItem(invPos);
			if (item != null) {
				Sprite itemIcon = new Sprite(item.getTexture());
				itemIcon.setPosition(xPos, yPos);
				display.drawSprite(itemIcon);
			}

			invPos++;
		}
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub

	}

}
