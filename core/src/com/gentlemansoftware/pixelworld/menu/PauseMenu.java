package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuNameTypes;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuRunnableItem;

public class PauseMenu extends SimpleMenu {

	Menu optionMenu;
	Menu mapMenu;
	Menu worldMenu;
	
	public PauseMenu(MenuHandler menuHandler, Menu parent) {
		super(menuHandler, parent, "Pause", null);
		
		optionMenu = new OptionMenu(handler, this);
		mapMenu = new MapMenu(handler,this);
		worldMenu = new WorldMenu(handler,this);
		
		setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();
		menuComponents.add(this.handler.ingameMenu);
		menuComponents.add(mapMenu);
		menuComponents.add(worldMenu);
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
