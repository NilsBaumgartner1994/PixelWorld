package com.gentlemansoftware.pixelworld.menu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.entitys.Human;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.inputs.GamePadButtons;
import com.gentlemansoftware.pixelworld.items.AbstractItem;
import com.gentlemansoftware.pixelworld.menuComponents.ChatOverlay;
import com.gentlemansoftware.pixelworld.menuComponents.ControllerOverlay;
import com.gentlemansoftware.pixelworld.menuComponents.MiniMapOverlay;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;

public class Ingame extends SimpleMenu {

	private ControllerOverlay controlleroverlay;
	private MiniMapOverlay minimap;
	private ChatOverlay chatoverlay;

	public Ingame(MenuHandler handler, Menu parent) {
		super(handler, parent, "Resume", null);
		this.drawConnectors = false;
		initMenuComponents();
	}

	private void initMenuComponents() {
		controlleroverlay = new ControllerOverlay(this.handler.user.gamepad);
		this.addContent(controlleroverlay);

		minimap = new MiniMapOverlay(this.handler);
		this.addContent(minimap);

		chatoverlay = new ChatOverlay(this.handler);
		this.addContent(chatoverlay);
	}

	@Override
	public void render(CameraControllerInterface display) {
		super.render(display);
		drawIconBar(display);
	}

	@Override
	public boolean update(GamePad gamepad) {
		if (gamepad.isButtonTyped(GamePadButtons.ESC)) {
			this.handler.setActivMenu(this.handler.pauseMenu);
		}
		User user = this.handler.user;
		if (gamepad.isButtonTyped(GamePadButtons.LEFTPAD_DOWN)) {
			user.cameraController.changeDistance(1);
		}
		if (gamepad.isButtonTyped(GamePadButtons.LEFTPAD_UP)) {
			user.cameraController.changeDistance(-1);
		}
		if (gamepad.isButtonTyped(GamePadButtons.START)) {
			if (user.network != null) {
				if (user.network.gameClient.isConnected()) {
					user.network.gameClient.waitForTextInputAndSendMessage();
				} 
			}
		}
		if (gamepad.isButtonTyped(GamePadButtons.R2)) {
			user.cameraController.rotateCamera(90);
			// human.use(human.getNextBlockInDirection());
		}

		user.updateControlledEntitys();

		Human human = user.human;

		if (human != null) {
			human.run(gamepad.isButtonPressed(GamePadButtons.SHIFT));
			if (gamepad.isButtonTyped(GamePadButtons.LEFTPAD_LEFT)) {
				human.inventory.setActivSlot(human.inventory.getActivSlot() - 1);
			}
			if (gamepad.isButtonTyped(GamePadButtons.LEFTPAD_RIGHT)) {
				human.inventory.setActivSlot(human.inventory.getActivSlot() + 1);
			}
			if(gamepad.isButtonTyped(GamePadButtons.RIGHTSHOULDER)){
				Position posAtWorld = user.cameraController.getGlobalPosFromScreenPos((int) gamepad.getCursor().pos.x, user.cameraController.getHeight() - (int) gamepad.getCursor().pos.y);
				human.use(posAtWorld);
			}
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
		if (human != null) {
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
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub

	}

}
