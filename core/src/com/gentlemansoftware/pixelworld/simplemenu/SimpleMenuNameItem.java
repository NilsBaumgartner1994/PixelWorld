package com.gentlemansoftware.pixelworld.simplemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.profiles.VarHolder;

public class SimpleMenuNameItem implements SimpleMenuComponent{

	private String title;
	boolean active;
	SimpleMenuNameTypes type;
	
	public SimpleMenuNameItem(String title, SimpleMenuNameTypes type) {
		setTitle(title);
		setType(type);
		setActive(false);
	}
	
	public void setType(SimpleMenuNameTypes type){
		this.type = type;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	private int drawOptionMenu(CameraControllerInterface display, int yposStart) {
		Sprite menu_title = new Sprite(SimpleMenuNameTypes.getTexture(type));

		int xpos = display.getWidth() / 2 - menu_title.getRegionWidth() / 2;
		int ypos = yposStart;

		Color oldColor = display.getFont().getColor().cpy();
		display.getFont().setColor(getColor());
		display.getLayout().setText(display.getFont(), this.getTitle());
		int stringWidth = (int) display.getLayout().width;
		int stringHeight = (int) display.getLayout().height;

		ypos -= menu_title.getRegionHeight();
		menu_title.setPosition(xpos, ypos);
		display.drawSprite(menu_title);
		display.getFont().draw(display.getSpriteBatch(), this.getTitle(), display.getWidth() / 2 - stringWidth / 2,
				ypos + menu_title.getRegionHeight() / 2 + stringHeight / 2);

		display.getFont().setColor(oldColor);

		return ypos;
	}
	
	public Color getColor() {
		return this.active ? Color.GOLD : Color.FIREBRICK;
	}

	@Override
	public boolean update(GamePad gamepad) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int render(CameraControllerInterface display, int ypos) {
		ypos = drawOptionMenu(display, ypos);
		return ypos;
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public String getTitle() {
		return title;
	}

}
