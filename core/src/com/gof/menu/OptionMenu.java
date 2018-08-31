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

public class OptionMenu implements Menu {

	public MenuHandler menuHandler;
	private MenuComponent activeMenuComponent;

	private List<MenuComponent> menuComponents;
	private MenuComponent mapTileCoordinatesMenuComponent;
	private MenuComponent debugInformationsMenuComponent;
	private MenuComponent backMenuComponent;
	private Menu parent;

	public OptionMenu(MenuHandler menuHandler, Menu parent) {
		this.menuHandler = menuHandler;
		this.parent = parent;
		initMenuComponents();
		mapTileCoordinatesMenuComponent.setActive(true);
		activeMenuComponent = mapTileCoordinatesMenuComponent;
	}

	public void initMenuComponents() {
		menuComponents = new ArrayList<MenuComponent>();
		mapTileCoordinatesMenuComponent = new MenuComponent("MapTileCoordinates", ""+this.menuHandler.user.profile.showDebugInformationCoordinatesOnMapTiles);
		menuComponents.add(mapTileCoordinatesMenuComponent);
		debugInformationsMenuComponent = new MenuComponent("Side Informations", ""+this.menuHandler.user.profile.showDebugInformationSide);
		menuComponents.add(debugInformationsMenuComponent);
		backMenuComponent = new MenuComponent("Back", "to Pause Menu");
		menuComponents.add(backMenuComponent);
	}

	@Override
	public void update(GamePad gamepad) {
		if (gamepad.getButton(GamePadButtons.ESC).isTyped()) {
			menuHandler.setActivMenu(parent);
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
		if (activeMenuComponent == mapTileCoordinatesMenuComponent) {
			this.menuHandler.user.profile.showDebugInformationCoordinatesOnMapTiles = !this.menuHandler.user.profile.showDebugInformationCoordinatesOnMapTiles;
			mapTileCoordinatesMenuComponent.content = ""+this.menuHandler.user.profile.showDebugInformationCoordinatesOnMapTiles;
		}
		if (activeMenuComponent == debugInformationsMenuComponent) {
			this.menuHandler.user.profile.showDebugInformationSide = !this.menuHandler.user.profile.showDebugInformationSide;
			debugInformationsMenuComponent.content = ""+this.menuHandler.user.profile.showDebugInformationSide;
		}
		if (activeMenuComponent == backMenuComponent) {
			this.menuHandler.setActivMenu(this.parent);
		}
	}

	@Override
	public void changeSelection(Vector2 vec) {
		// TODO Auto-generated method stub

	}

}
