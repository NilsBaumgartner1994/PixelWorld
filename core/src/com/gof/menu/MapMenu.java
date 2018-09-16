package com.gof.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.gof.simplemenu.SimpleMenu;
import com.gof.simplemenu.SimpleMenuComponent;
import com.gof.simplemenu.SimpleMenuImage;
import com.gof.simplemenu.SimpleMenuNameTypes;
import com.gof.simplemenu.SimpleMenuRunnableItem;
import com.gof.world.WorldToPNG;

public class MapMenu extends SimpleMenu {

	SimpleMenuImage menuImage;

	public MapMenu(MenuHandler menuHandler, Menu parent) {
		super(menuHandler, parent, "Map", null);
		setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();
		menuImage = new SimpleMenuImage("Map", SimpleMenuNameTypes.SUB);
		menuComponents.add(menuImage);
		menuComponents.add(parent);

		return menuComponents;
	}

	@Override
	public void select() {
		Pixmap map = WorldToPNG.getPixmap(this.handler.user.human.getMapTile().chunk);
		menuImage.setImage(map);
		map.dispose();
		System.out.println("MenuImage Set");

		super.select();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		menuImage.dispose();
	}

}
