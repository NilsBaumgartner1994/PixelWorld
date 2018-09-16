package com.gof.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.gof.simplemenu.SimpleMenu;
import com.gof.simplemenu.SimpleMenuComponent;
import com.gof.simplemenu.SimpleMenuNameTypes;
import com.gof.simplemenu.SimpleMenuRunnableItem;

public class PauseMenu extends SimpleMenu {

	Menu optionMenu;
	Menu mapMenu;
	
	public PauseMenu(MenuHandler menuHandler, Menu parent) {
		super(menuHandler, parent, "Pause", null);
		
		optionMenu = new OptionMenu(menuHandler, this);
		mapMenu = new MapMenu(menuHandler,this);
		
		setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();
		menuComponents.add(this.handler.ingameMenu);
		menuComponents.add(mapMenu);
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
