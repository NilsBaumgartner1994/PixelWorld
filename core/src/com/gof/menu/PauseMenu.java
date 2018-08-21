package com.gof.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.gof.Inputs.GamePad;
import com.gof.Inputs.GamePadButtons;
import com.gof.game.CameraController;
import com.gof.game.ResourceLoader;

public class PauseMenu implements Menu {

	public MenuHandler menuHandler;

	public PauseMenu(MenuHandler menuHandler) {
		this.menuHandler = menuHandler;
	}

	@Override
	public void update(GamePad gamepad) {
		if(gamepad.getButton(GamePadButtons.ESC).isTyped()){
			menuHandler.setActivMenu(menuHandler.ingameMenu);
		}
	}
	
	@Override
	public void render(CameraController display) {
		drawOptionMenu(display);
	}
	
	private void drawOptionMenu(CameraController display) {

		Sprite title = new Sprite(ResourceLoader.getInstance().getGUI("menu_title"));
		Sprite chain = new Sprite(ResourceLoader.getInstance().getGUI("menu_chain"));
		Sprite post_title = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_top"));
		Sprite post_top = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_top"));
		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_middle"));
		Sprite post_bottom = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_bottom"));

		int marginTop = 50;
		int xpos = display.width / 2 - title.getRegionWidth() / 2;
		int yposStart = display.height - marginTop;

		String[] labels = { "Resume", "Options", "Quit" };

		Color oldColor = display.font.getColor().cpy();
		display.font.setColor(Color.FIREBRICK);

		int ypos = yposStart;
		for (String label : labels) {

			ypos -= title.getRegionHeight();
			title.setPosition(xpos, ypos);
			display.drawSprite(title);

			display.layout.setText(display.font, label);
			int stringWidth = (int) display.layout.width;
			int stringHeight = (int) display.layout.height;
			display.font.draw(display.fboBatch, label, display.width / 2 - stringWidth / 2,
					ypos + title.getRegionHeight() / 2 + stringHeight / 2);

			ypos = display.drawSpriteAndSubtractYpos(chain, xpos, ypos);
			ypos = display.drawSpriteAndSubtractYpos(post_title, xpos, ypos);
			ypos = display.drawSpriteAndSubtractYpos(post_top, xpos, ypos);
			ypos = display.drawSpriteAndSubtractYpos(post_middle, xpos, ypos);
			ypos = display.drawSpriteAndSubtractYpos(post_bottom, xpos, ypos);

		}

		display.font.setColor(oldColor);
	}

}
