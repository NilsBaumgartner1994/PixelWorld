package com.gof.menu;

import java.util.LinkedList;
import java.util.List;
import com.gof.game.Main;
import com.gof.profiles.VarHolder;
import com.gof.simplemenu.SimpleMenu;
import com.gof.simplemenu.SimpleMenuBooleanEditable;
import com.gof.simplemenu.SimpleMenuComponent;
import com.gof.simplemenu.SimpleMenuNameTypes;
import com.gof.simplemenu.SimpleMenuRunnableItem;
import com.gof.world.WorldToPNG;

public class OptionMenu extends SimpleMenu {

	public OptionMenu(MenuHandler menuHandler, Menu parent) {
		super(menuHandler, parent, "Options", null);
		this.setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();

		for (VarHolder var : this.handler.user.profile.debugProfile.getVars()) {
			SimpleMenuComponent c = new SimpleMenuBooleanEditable(var);
			menuComponents.add(c);
		}
		
		Runnable quitRunnable = new Runnable() {
			public void run() {
				WorldToPNG.saveToImage(Main.getInstance().titleScreenWorld);
			}
		};
		
		SimpleMenuRunnableItem WorldToPNG = new SimpleMenuRunnableItem("World To PNG", SimpleMenuNameTypes.SUB,quitRunnable);
		menuComponents.add(WorldToPNG);
		
		menuComponents.add(parent);

		return menuComponents;
	}

}
