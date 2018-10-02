package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.inputs.GamePadButtons;
import com.gentlemansoftware.pixelworld.inputs.Stick;
import com.gentlemansoftware.pixelworld.entitys.Human;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuNameTypes;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuRunnableItem;
import com.gentlemansoftware.pixelworld.sound.EasySounds;
import com.gentlemansoftware.pixelworld.sound.SoundManager;
import com.gentlemansoftware.pixelworld.items.AbstractItem;
import com.gentlemansoftware.pixelworld.menuComponents.ChatOverlay;
import com.gentlemansoftware.pixelworld.menuComponents.ControllerOverlay;
import com.gentlemansoftware.pixelworld.menuComponents.MiniMapOverlay;
import com.gentlemansoftware.pixelworld.physics.Direction;

public class MainMenu extends SimpleMenu {

	Menu multiplayerMenu;
	Menu worldMenu;

	public MainMenu(MenuHandler handler, Menu parent) {
		super(handler, parent, "Back", null);
		setContent(initMenuComponents());
	}

	private List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();
		multiplayerMenu = new MultiplayerMenu(handler, this);
		menuComponents.add(multiplayerMenu);
		
		worldMenu = new WorldMenu(handler,this);
		menuComponents.add(worldMenu);

		Runnable quitRunnable = new Runnable() {
			public void run() {
				Gdx.app.exit();
			}
		};

		SimpleMenuRunnableItem quitMenuComponent = new SimpleMenuRunnableItem("Quit", SimpleMenuNameTypes.MAIN,
				quitRunnable);
		menuComponents.add(quitMenuComponent);

		return menuComponents;
	}

	@Override
	public void render(CameraControllerInterface display) {
		super.render(display);
	}

	

}
