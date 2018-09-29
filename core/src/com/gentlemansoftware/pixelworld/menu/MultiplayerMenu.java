package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.profiles.UserProfile;
import com.gentlemansoftware.pixelworld.profiles.VarHolder;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuBooleanEditable;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuFloatEditable;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuNameTypes;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuRunnableItem;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuVarHolderComponentHelper;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuVarholder;
import com.gentlemansoftware.pixelworld.world.WorldToPNG;

public class MultiplayerMenu extends SimpleMenu {

	public MultiplayerMenu(MenuHandler handler, Menu parent) {
		super(handler, parent, "Multiplayer", null);
		this.setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();

		Runnable connectRunnable = new Runnable() {
			public void run() {
				handler.user.network.connectToLocalServer();
			}
		};

		SimpleMenuRunnableItem connectToLocal = new SimpleMenuRunnableItem("Connect", SimpleMenuNameTypes.SUB,
				connectRunnable);
		
		Runnable hostRunnable = new Runnable() {
			public void run() {
				handler.user.network.hostServer();
			}
		};

		SimpleMenuRunnableItem hostLocal = new SimpleMenuRunnableItem("Host", SimpleMenuNameTypes.SUB,
				hostRunnable);
		
		
		Runnable messageRunnable = new Runnable() {
			public void run() {
				handler.user.network.sendMessage();
			}
		};

		SimpleMenuRunnableItem messageItem = new SimpleMenuRunnableItem("Message", SimpleMenuNameTypes.SUB,
				messageRunnable);
		

		menuComponents.add(hostLocal);
		menuComponents.add(connectToLocal);
		menuComponents.add(messageItem);

		menuComponents.add(parent);

		return menuComponents;
	}

}
