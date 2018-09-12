package com.gof.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;
import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;

public class MenuComponentString implements MenuComponentContent{

	String content;
	boolean active;

	public MenuComponentString(String content) {
		setContent(content);
		setActive(false);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int render(CameraControllerInterface display, int yposStart) {
		int ypos = yposStart;
		ypos = drawContent(display, ypos,content);
		return ypos;
	}
	
	private int drawContent(CameraControllerInterface display, int ypos, String content) {
		String[] contents = content.split("\n");
		for(String c : contents){
			ypos = drawSingleContent(display,ypos,c);
		}
		return ypos;
	}

	private int drawSingleContent(CameraControllerInterface display,int ypos, String content) {
		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_middle"));
		int xpos = display.getWidth() / 2 - post_middle.getRegionWidth() / 2;

		ypos = display.drawSpriteAndSubtractYpos(post_middle, xpos, ypos);
		display.getLayout().setText(display.getFont(), content);
		int stringWidth = (int) display.getLayout().width;
		int stringHeight = (int) display.getLayout().height;
		display.getFont().draw(display.getSpriteBatch(), content, display.getWidth() / 2 - stringWidth / 2,
				ypos + post_middle.getRegionHeight() / 2 + stringHeight / 2);
		
		return ypos;
	}

	public Color getColor() {
		return this.active ? Color.GOLD : Color.FIREBRICK;
	}

}
