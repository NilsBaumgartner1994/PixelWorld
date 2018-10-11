package com.gentlemansoftware.pixelworld.simplemenu;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.gentlemansoftware.pixelworld.menu.Menu;
import com.gentlemansoftware.pixelworld.menu.MenuHandler;
import com.gentlemansoftware.pixelworld.profiles.VarHolder;

public class SimpleMenuVarholder extends SimpleMenu {

	public SimpleMenuVarholder(MenuHandler handler, Menu parent, String title, List<VarHolder<? extends Serializable>> list) {
		super(handler, parent, title, null);
		this.setContent(initMenuComponents(list));
	}

	public List<SimpleMenuComponent> initMenuComponents(List<VarHolder<? extends Serializable>> list) {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();
		menuComponents.addAll(SimpleMenuVarHolderComponentHelper.getRightComponents(list));
		menuComponents.add(parent);

		return menuComponents;
	}

}
