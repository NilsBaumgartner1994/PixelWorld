package com.gof.menu;

import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;
import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;
import com.gof.profiles.VarHolder;

public class MenuComponent {

	String title;
	MenuComponentContent[] contents;
	boolean active;

	public MenuComponent(String title, MenuComponentContent[] content) {
		setTitle(title);
		setContent(content);
		setActive(false);
	}

	public MenuComponent(String title, Object... obj) {
		this(title, createMenuComponentContents(obj));
	}

	private static MenuComponentContent[] createMenuComponentContents(Object... obj) {
		MenuComponentContent[] contents = new MenuComponentContent[obj.length];
		for (int i = 0; i < obj.length; i++) {
			if (obj[i].getClass() == VarHolder.class) {
				VarHolder varHolderGen = (VarHolder)obj[i];
				if(varHolderGen.value.getClass() == Boolean.class){
					contents[i] = new MenuComponentBoolean("Test", (VarHolder<Boolean>)obj[i]);
				}
				
			}
			if (obj[i].getClass() == String.class) {
				contents[i] = new MenuComponentString((String) obj[i]);
			}
		}
		return contents;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(MenuComponentContent[] content) {
		this.contents = content;
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

		for (MenuComponentContent c : contents) {
			ypos = c.render(display, ypos);
		}

		ypos = display.drawSpriteAndSubtractYpos(post_bottom, xpos, ypos);

		display.getFont().setColor(oldColor);

		return ypos;
	}

	public Color getColor() {
		return this.active ? Color.GOLD : Color.FIREBRICK;
	}

}
