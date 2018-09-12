package com.gof.menu;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;
import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;
import com.gof.profiles.VarHolder;

public class MenuComponentBoolean implements MenuComponentContent {

	String name;
	VarHolder<Boolean> bool;
	boolean active;

	public MenuComponentBoolean(String name, VarHolder<Boolean> obj) {
		setName(name);
		setContent(obj);
		setActive(false);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(VarHolder<Boolean> content) {
		this.bool = content;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int render(CameraControllerInterface display, int yposStart) {
		int ypos = yposStart;

		ypos = drawSingleContent(display, ypos, name);
		
		int percent = this.bool.value ? 100 : 0;
		drawSlider(display, yposStart, percent);
		return ypos;
	}

	private int drawSingleContent(CameraControllerInterface display, int ypos, String content) {
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

	private int drawSlider(CameraControllerInterface display, int ypos, int percent) {
		percent = Math.max(0, Math.min(100, percent));

		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_middle"));
		Sprite slider_body = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_slider_body"));
		Sprite slider_knob = new Sprite(ResourceLoader.getInstance().getGUI("menu_information_post_slider_knob"));

		int xpos = display.getWidth() / 2 - post_middle.getRegionWidth() / 2;

		display.drawSpriteAndSubtractYpos(post_middle, xpos, ypos);
		display.drawSpriteAndSubtractYpos(slider_body, xpos, ypos);
		ypos = display.drawSpriteAndSubtractYpos(slider_knob, xpos + percent, ypos);

		return ypos;
	}

	public Color getColor() {
		return this.active ? Color.GOLD : Color.FIREBRICK;
	}

}
