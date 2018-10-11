package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuImage;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuNameTypes;
import com.gentlemansoftware.pixelworld.world.WorldToPNG;

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
	public void dispose() {
		// TODO Auto-generated method stub
		menuImage.dispose();
	}
	
	@Override
	public void prepareForActivation(){
		Pixmap map = WorldToPNG.getPixmap(this.handler.user.human.getMapTile().chunk);
		menuImage.setImage(map);
		map.dispose();
	}

}
