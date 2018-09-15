package com.gof.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;

public class PauseMenu extends SimpleMenu {

	Menu optionMenu;
	
	public PauseMenu(MenuHandler menuHandler, Menu parent) {
		super(menuHandler, parent, "Pause", null);
		optionMenu = new OptionMenu(menuHandler, this);
		setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();
		menuComponents.add(this.handler.ingameMenu);
		menuComponents.add(optionMenu);
		
		Runnable quitRunnable = new Runnable() {
			public void run() {
				Gdx.app.exit();
			}
		};
		
		SimpleMenuRunnableItem quitMenuComponent = new SimpleMenuRunnableItem("Quit", SimpleMenuNameTypes.MAIN,quitRunnable);
		menuComponents.add(quitMenuComponent);
		
		return menuComponents;
	}

}
