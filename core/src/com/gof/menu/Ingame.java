package com.gof.menu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;
import com.gof.entitys.Human;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;
import com.gof.profiles.User;

import com.gof.items.AbstractItem;

public class Ingame implements Menu {

	public MenuHandler menuHandler;

	public Ingame(MenuHandler menuHandler) {
		this.menuHandler = menuHandler;
	}

	@Override
	public void update(GamePad gamepad) {

		if (gamepad.isButtonTyped(GamePadButtons.ESC)) {
			menuHandler.setActivMenu(menuHandler.pauseMenu);
		}
		User user = this.menuHandler.user;
		if (gamepad.isButtonTyped(GamePadButtons.UP)) {
			System.out.println("ZoomIn");
			user.cameraController.changeDistance(1);
		}
		if (gamepad.isButtonTyped(GamePadButtons.DOWN)) {
			System.out.println("ZoomOut");
			user.cameraController.changeDistance(-1);
		}

		Human human = user.human;
		switch (this.menuHandler.user.cameraController.getCameraDirection()) {
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
	}

	@Override
	public void render(CameraControllerInterface display) {
		// TODO Auto-generated method stub
		drawIconBar(display);
	}

	public void drawIconBar(CameraControllerInterface display) {

		Sprite framebar = new Sprite(ResourceLoader.getInstance().getIcon("framebar"));
		framebar.setPosition(display.getWidth() / 2 - framebar.getRegionWidth() / 2, 10);
		display.drawSprite(framebar);

		Sprite iconFrame = new Sprite(ResourceLoader.getInstance().getIcon("frame"));
		Sprite activIconFrame = new Sprite(ResourceLoader.getInstance().getIcon("frame_activ"));

		Human human = menuHandler.user.human;
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

	@Override
	public void changeSelection(Vector2 vec) {
		// TODO Auto-generated method stub
		
	}

}
