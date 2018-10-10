package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuImage;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuNameTypes;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuRunnableItem;
import com.gentlemansoftware.pixelworld.world.TileWorld;
import com.gentlemansoftware.pixelworld.world.WorldToPNG;

public class WorldMenu extends SimpleMenu {

	TileWorld world;

	public WorldMenu(MenuHandler menuHandler, Menu parent) {
		super(menuHandler, parent, "World", null);
		setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();

		Runnable saveRunnable = new Runnable() {
			public void run() {
				handler.user.getTileWorld().save();
			}
		};

		SimpleMenuRunnableItem saveMenuComponent = new SimpleMenuRunnableItem("TODO: Save", SimpleMenuNameTypes.SUB,
				saveRunnable);
		menuComponents.add(saveMenuComponent);

		Runnable loadRunnable = new Runnable() {
			public void run() {
				if (handler.user.network.gameServer.isAlive()) {
					handler.user.network.gameServer.gameWorld = TileWorld.load("Default");
				}
			}
		};

		SimpleMenuRunnableItem loadMenuComponent = new SimpleMenuRunnableItem("TODO: Load", SimpleMenuNameTypes.SUB,
				loadRunnable);
		menuComponents.add(loadMenuComponent);

		menuComponents.add(parent);

		return menuComponents;
	}

}
