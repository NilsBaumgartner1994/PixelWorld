package com.gentlemansoftware.pixelworld.simplemenu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.helper.Rectangle;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.inputs.GamePadButtons;
import com.gentlemansoftware.pixelworld.menu.Menu;
import com.gentlemansoftware.pixelworld.menu.MenuHandler;

public class SimpleMenu extends SimpleMenuNameItem implements Menu {

	protected List<SimpleMenuComponent> contents;
	protected List<SimpleMenuComponent> noCahincontents;

	protected SimpleMenuComponent active = null;
	protected MenuHandler handler;
	protected Menu parent;
	protected boolean drawConnectors;
	protected boolean drawCursor;

	public SimpleMenu(MenuHandler handler, Menu parent, String title, List<SimpleMenuComponent> content) {
		super(title, SimpleMenuNameTypes.MAIN);
		this.handler = handler;
		this.drawConnectors = true;
		this.drawCursor = true;
		noCahincontents = new LinkedList<SimpleMenuComponent>();
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

	public void addContent(SimpleMenuComponent content) {
		contents.add(content);
	}

	public void removeContent(SimpleMenuComponent content) {
		contents.remove(content);
	}

	public void addNoChainContent(SimpleMenuComponent content) {
		noCahincontents.add(content);
	}

	public void removeNoChainContent(SimpleMenuComponent content) {
		noCahincontents.remove(content);
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
		if (gamepad.getButton(GamePadButtons.LEFTPAD_UP).isTyped()) {
			changeActiveMenuComponent(-1);
		}
		if (gamepad.getButton(GamePadButtons.LEFTPAD_DOWN).isTyped()) {
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

		if (gamepad.getCursor().left.isTyped()) {
			this.checkTouchAsMenu((int) gamepad.getCursor().getPos().x, (int) gamepad.getCursor().getPos().y);
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

		int ypos = (int) (display.getHeight() - chain.getHeight());

		int size = contents.size();
		for (int i = 0; i < size; i++) {
			SimpleMenuComponent item = contents.get(i);
			String helper = "ERROR";
			boolean isParent = (item == parent);
			if (isParent) {
				SimpleMenu menu = (SimpleMenu) item;
				helper = menu.getTitle();
				menu.setTitle("Back");
			}
			ypos = item.render(display, ypos);
			if (i < size - 1) {
				if (this.drawConnectors) {
					ypos = display.drawSpriteAndSubtractYpos(chain, xpos, ypos);
				}
			}

			if (isParent) {
				SimpleMenu menu = (SimpleMenu) item;
				menu.setTitle(helper);
			}
		}

		for (SimpleMenuComponent conent : this.noCahincontents) {
			ypos = conent.render(display, ypos);
		}

		if (this.drawCursor) {
			drawCursor(display);
		}
	}

	public void drawCursor(CameraControllerInterface display) {
		Sprite hand = new Sprite(ResourceLoader.getInstance().getGUI("cursor/hand_select"));
		Vector2 pos = handler.user.gamepad.getCursor().getPos();

		hand.setPosition(pos.x - hand.getRegionWidth() / 2, display.getHeight() - pos.y - hand.getRegionHeight() / 2);
		display.drawSprite(hand);
	}

	@Override
	public void select() {
		if (active instanceof Menu && active != this) {
			this.dispose();
			this.handler.setActivMenu((Menu) active);
		} else {
			if (active != null) {
				active.select();
			}
		}
	}

	@Override
	public void prepareForActivation() {
		// TODO Auto-generated method stub

	}

	public void checkTouchAsMenu(int x, int y) {
		for (SimpleMenuComponent item : contents) {
			Rectangle reg = item.getTouchRegion();
			if (reg != null && reg.isInRegion(x, y)) {
				active = item;
			}
		}

		for (SimpleMenuComponent item : noCahincontents) {
			Rectangle reg = item.getTouchRegion();
			if (reg != null && reg.isInRegion(x, y)) {
				active = item;
			}
		}

		select();
	}

}
