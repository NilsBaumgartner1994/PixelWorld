package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.menuComponents.ChatOverlay;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuNameTypes;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuRunnableItem;

public class MainMenu extends SimpleMenu {

	public MultiplayerMenu multiplayerMenu;
	Menu optionMenu;
	Menu userProfiles;
	public Menu worldMenu;
	public ChatOverlay chatoverlay;

	public MainMenu(MenuHandler handler, Menu parent) {
		super(handler, parent, "Main Menu", null);
		setContent(initMenuComponents());
	}

	private List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();
		multiplayerMenu = new MultiplayerMenu(handler, this);
		
		menuComponents.add(multiplayerMenu);

		optionMenu = new OptionMenu(handler, this);
		menuComponents.add(optionMenu);
		
		userProfiles = new UserProfileListMenu(handler,this);
		menuComponents.add(userProfiles);
		
		worldMenu = new WorldMenu(handler, this);
		menuComponents.add(worldMenu);

		Runnable quitRunnable = new Runnable() {
			public void run() {
				Gdx.app.exit();
			}
		};

		SimpleMenuRunnableItem quitMenuComponent = new SimpleMenuRunnableItem("Quit", SimpleMenuNameTypes.MAIN,
				quitRunnable);
		menuComponents.add(quitMenuComponent);
		
		chatoverlay = new ChatOverlay(this.handler);
		this.addNoChainContent(chatoverlay);

		return menuComponents;
	}

	@Override
	public void render(CameraControllerInterface display) {
		super.render(display);
	}

}
