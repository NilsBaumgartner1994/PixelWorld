package com.gentlemansoftware.pixelworld.simplemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.profiles.VarHolder;

public class SimpleMenuBooleanEditable implements SimpleMenuComponent{

	VarHolder<Boolean> bool;
	boolean active;

	public SimpleMenuBooleanEditable(VarHolder<Boolean> obj) {
		setContent(obj);
		setActive(false);
	}

	public void setContent(VarHolder<Boolean> content) {
		this.bool = content;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int render(CameraControllerInterface display, int yposStart) {
		int ypos = yposStart;
		int helper = ypos;
		
		ypos = drawSingleContent(display, ypos);
		drawText(display,this.bool.getName(),helper);
		helper = ypos;
		
		ypos = drawSingleContent(display, ypos);
		int percent = this.bool.value ? 100 : 0;
		drawSlider(display, helper, percent);
		
		return ypos;
	}

	private int drawSingleContent(CameraControllerInterface display, int ypos) {
		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menus/menu_information_top"));
		int xpos = display.getWidth() / 2 - post_middle.getRegionWidth() / 2;

		ypos = display.drawSpriteAndSubtractYpos(post_middle, xpos, ypos);
		return ypos;
	}
	
	private void drawText(CameraControllerInterface display, String text, int ypos){
		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menus/menu_information_top"));
	
		display.getFont().setColor(getColor());
		display.getLayout().setText(display.getFont(), text);
		int stringWidth = (int) display.getLayout().width;
		int stringHeight = (int) display.getLayout().height;

		int xpos = display.getWidth() / 2;
		
		display.getFont().draw(display.getSpriteBatch(), text, xpos-stringWidth/2,
				ypos - post_middle.getHeight()/2 + stringHeight / 2);
	}

	private int drawSlider(CameraControllerInterface display, int ypos, int percent) {
		percent = Math.max(0, Math.min(100, percent));

		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menus/menu_information_bottom"));
		Sprite slider_body = new Sprite(ResourceLoader.getInstance().getGUI("menus/menu_information_post_slider_body"));
		Sprite slider_knob = new Sprite(ResourceLoader.getInstance().getGUI("menus/menu_information_post_slider_knob"));

		int xpos = display.getWidth() / 2 - post_middle.getRegionWidth() / 2;

		display.drawSpriteAndSubtractYpos(post_middle, xpos, ypos);
		display.drawSpriteAndSubtractYpos(slider_body, xpos, ypos);
		ypos = display.drawSpriteAndSubtractYpos(slider_knob, xpos + percent, ypos);

		return ypos;
	}

	public Color getColor() {
		return this.active ? Color.GOLD : Color.FIREBRICK;
	}

	@Override
	public boolean update(GamePad gamepad) {

		return true;
	}

	@Override
	public void select() {
		bool.setVar(!bool.getVar());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
