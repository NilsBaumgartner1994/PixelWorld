package com.gof.simplemenu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;
import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;
import com.gof.menu.Menu;
import com.gof.menu.MenuHandler;

public class SimpleMenu extends SimpleMenuNameItem implements Menu {

	protected List<SimpleMenuComponent> contents;
	protected SimpleMenuComponent active = null;
	protected MenuHandler handler;
	protected Menu parent;

	public SimpleMenu(MenuHandler handler, Menu parent, String title, List<SimpleMenuComponent> content) {
		super(title, SimpleMenuNameTypes.MAIN);
		this.handler = handler;
		setParent(parent);
		setContent(content);
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public void setContent(List<SimpleMenuComponent> content) {
		if (content == null) {
			content = new LinkedList<SimpleMenuComponent>();
		}
		if (content != null) {
			if (!content.isEmpty()) {
				setActive(content.get(0));
			} else {
				setActive(null);
			}
		}
		this.contents = content;
	}
	
	public void addContent(SimpleMenuComponent content){
		contents.add(content);
	}

	public void setActive(SimpleMenuComponent simpleMenuComponent) {
		if (this.active != null) {
			this.active.setActive(false);
		}
		if (simpleMenuComponent != null) {
			simpleMenuComponent.setActive(true);
		}
		this.active = simpleMenuComponent;
	}

	@Override
	public boolean update(GamePad gamepad) {
		if (gamepad.getButton(GamePadButtons.ESC).isTyped()) {
			setActive(parent);
			select();
		}
		if (gamepad.getButton(GamePadButtons.UP).isTyped()) {
			changeActiveMenuComponent(-1);
		}
		if (gamepad.getButton(GamePadButtons.DOWN).isTyped()) {
			changeActiveMenuComponent(1);
		}

		switch (gamepad.getLeftStick().getLastDirection()) {
		case NORTH:
			changeActiveMenuComponent(-1);
			break;
		case SOUTH:
			changeActiveMenuComponent(1);
			break;
		default:
			break;
		}

		if (gamepad.getButton(GamePadButtons.START).isTyped()) {
			select();
		}

		return false;
	}

	public void changeActiveMenuComponent(int direction) {
		if (contents != null && contents.size() != 0) {
			int size = contents.size();
			int position = getMenuComponentPosition(active, size);
			int newActiveMenuPos = ((position + direction) % size + size) % size;
			SimpleMenuComponent newActive = contents.get(newActiveMenuPos);
			setActive(newActive);
		}
	}

	private int getMenuComponentPosition(SimpleMenuComponent comp, int size) {
		for (int i = 0; i < size; i++) {
			if (contents.get(i) == comp)
				return i;
		}
		return 0;
	}

	@Override
	public void render(CameraControllerInterface display) {
		Sprite chain = new Sprite(ResourceLoader.getInstance().getGUI("menus/menu_chain"));
		int xpos = display.getWidth() / 2 - chain.getRegionWidth() / 2;

		int ypos = (int) (display.getHeigth() - chain.getHeight());
		for (SimpleMenuComponent item : contents) {
			ypos = item.render(display, ypos);
			ypos = display.drawSpriteAndSubtractYpos(chain, xpos, ypos);
		}
	}

	@Override
	public void select() {
		if (active instanceof Menu && active != this) {
			this.dispose();
			this.handler.setActivMenu((Menu) active);
		} else {
			active.select();
		}
	}

	@Override
	public void prepareForActivation() {
		// TODO Auto-generated method stub
		
	}

}
