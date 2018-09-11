package com.gof.menuComponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;
import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;

public class MenuComponent {

	String title;
	String content;
	boolean active;

	public MenuComponent(String title, String content) {
		setTitle(title);
		setContent(content);
		setActive(false);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int render(CameraControllerInterface display, int yposStart) {
		int ypos = yposStart;
		ypos = drawOptionMenu(display, ypos);
		return ypos;
	}

	private int drawOptionMenu(CameraControllerInterface display, int yposStart) {
		Sprite menu_title = new Sprite(ResourceLoader.getInstance().getGUI("menu_title"));
		Sprite chain = new Sprite(ResourceLoader.getInstance().getGUI("menu_chain"));
		Sprite post_title = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_top"));
		Sprite post_top = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_top"));
		Sprite post_bottom = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_bottom"));

		int xpos = display.getWidth() / 2 - menu_title.getRegionWidth() / 2;
		int ypos = yposStart;

		Color oldColor = display.getFont().getColor().cpy();
		display.getFont().setColor(getColor());
		display.getLayout().setText(display.getFont(), this.title);
		int stringWidth = (int) display.getLayout().width;
		int stringHeight = (int) display.getLayout().height;

		ypos -= menu_title.getRegionHeight();
		menu_title.setPosition(xpos, ypos);
		display.drawSprite(menu_title);
		display.getFont().draw(display.getSpriteBatch(), this.title, display.getWidth() / 2 - stringWidth / 2,
				ypos + menu_title.getRegionHeight() / 2 + stringHeight / 2);

		ypos = display.drawSpriteAndSubtractYpos(chain, xpos, ypos);
		ypos = display.drawSpriteAndSubtractYpos(post_title, xpos, ypos);
		ypos = display.drawSpriteAndSubtractYpos(post_top, xpos, ypos);

		ypos = drawContent(display,xpos,ypos,this.content);
		ypos = drawSlider(display,xpos,ypos,30);

		ypos = display.drawSpriteAndSubtractYpos(post_bottom, xpos, ypos);

		display.getFont().setColor(oldColor);

		return ypos;
	}
	
	private int drawContent(CameraControllerInterface display, int xpos, int ypos, String content) {
		String[] contents = content.split("\n");
		for(String c : contents){
			ypos = drawSingleContent(display,xpos,ypos,c);
		}
		return ypos;
	}

	private int drawSingleContent(CameraControllerInterface display, int xpos, int ypos, String content) {
		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_middle"));

		ypos = display.drawSpriteAndSubtractYpos(post_middle, xpos, ypos);
		display.getLayout().setText(display.getFont(), content);
		int stringWidth = (int) display.getLayout().width;
		int stringHeight = (int) display.getLayout().height;
		display.getFont().draw(display.getSpriteBatch(), content, display.getWidth() / 2 - stringWidth / 2,
				ypos + post_middle.getRegionHeight() / 2 + stringHeight / 2);
		
		return ypos;
	}
	
	private int drawSlider(CameraControllerInterface display, int xpos, int ypos, int percent){
		percent = Math.max(0, Math.min(100, percent));
		
		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_middle"));
		Sprite slider_body = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_slider_body"));
		Sprite slider_knob = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_slider_knob"));
		
		display.drawSpriteAndSubtractYpos(post_middle, xpos, ypos);
		display.drawSpriteAndSubtractYpos(slider_body, xpos, ypos);
		System.out.println(percent);
		ypos = display.drawSpriteAndSubtractYpos(slider_knob, xpos+percent, ypos);
		
		return ypos;
	}

	public Color getColor() {
		return this.active ? Color.GOLD : Color.FIREBRICK;
	}

}
