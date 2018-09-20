package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.profiles.VarHolder;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuBooleanEditable;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuFloatEditable;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuNameTypes;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuRunnableItem;
import com.gentlemansoftware.pixelworld.world.WorldToPNG;

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
		
//		menuComponents.add(new SimpleMenuFloatEditable(this.handler.user.activGameWorld.time.timeSpeed));
		
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
