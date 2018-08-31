package com.gof.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.CameraControllerInterface;
import com.gof.game.ResourceLoader;
import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;
import com.gof.physics.Direction;

public class PauseMenu implements Menu {

	public MenuHandler menuHandler;
	private MenuComponent activeMenuComponent;

	private List<MenuComponent> menuComponents;
	private MenuComponent resumeMenuComponent;
	private MenuComponent optionsMenuComponent;
	private MenuComponent quitMenuComponent;
	
	private Menu optionMenu;

	public PauseMenu(MenuHandler menuHandler) {
		this.menuHandler = menuHandler;
		optionMenu = new OptionMenu(menuHandler,this);
		
		initMenuComponents();
		resumeMenuComponent.setActive(true);
		activeMenuComponent = resumeMenuComponent;
	}

	public void initMenuComponents() {
		menuComponents = new ArrayList<MenuComponent>();
		resumeMenuComponent = new MenuComponent("Resume", "Test String\nMultiple Line");
		menuComponents.add(resumeMenuComponent);
		optionsMenuComponent = new MenuComponent("Options", "Test String");
		menuComponents.add(optionsMenuComponent);
		quitMenuComponent = new MenuComponent("Quit", "Test String");
		menuComponents.add(quitMenuComponent);
	}

	@Override
	public void update(GamePad gamepad) {
		if (gamepad.getButton(GamePadButtons.ESC).isTyped()) {
			menuHandler.setActivMenu(menuHandler.ingameMenu);
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

	}

	public void changeActiveMenuComponent(int direction) {
		if (activeMenuComponent != null && !menuComponents.isEmpty()) {
			activeMenuComponent.setActive(false);
			int size = menuComponents.size();
			int position = getMenuComponentPosition(activeMenuComponent, size);
			int newActiveMenuPos = ((position + direction) % size + size) % size;
			activeMenuComponent = menuComponents.get(newActiveMenuPos);
			activeMenuComponent.setActive(true);
		}
	}

	private int getMenuComponentPosition(MenuComponent comp, int size) {
		for (int i = 0; i < size; i++) {
			if (menuComponents.get(i) == comp)
				return i;
		}
		return -1;
	}

	@Override
	public void render(CameraControllerInterface display) {
		int marginTop = 50;
		int yposStart = display.getHeigth() - marginTop;
		int ypos = yposStart;
		for (MenuComponent mc : menuComponents) {
			ypos = mc.render(display, ypos);
		}
	}

	@Override
	public void select() {
		if (activeMenuComponent == resumeMenuComponent) {
			this.menuHandler.setActivMenu(this.menuHandler.ingameMenu);
		}
		if (activeMenuComponent == optionsMenuComponent) {
			this.menuHandler.setActivMenu(optionMenu);
		}
		if (activeMenuComponent == quitMenuComponent) {
			Gdx.app.exit();
		}
	}

	@Override
	public void changeSelection(Vector2 vec) {
		// TODO Auto-generated method stub

	}

}
