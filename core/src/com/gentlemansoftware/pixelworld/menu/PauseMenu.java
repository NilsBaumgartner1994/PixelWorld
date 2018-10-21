package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;

import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;

public class PauseMenu extends SimpleMenu {

	Menu mapMenu;
	
	public PauseMenu(MenuHandler menuHandler, Menu parent) {
		super(menuHandler, parent, "Pause", null);
		mapMenu = new MapMenu(handler,this);
		
		setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();
		menuComponents.add(this.handler.ingameMenu);
//		menuComponents.add(mapMenu);
		menuComponents.add(handler.mainMenu);
		
		return menuComponents;
	}

}
