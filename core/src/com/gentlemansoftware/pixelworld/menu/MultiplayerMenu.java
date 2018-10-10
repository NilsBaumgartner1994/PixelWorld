package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;

import com.gentlemansoftware.easyGameNetwork.EasyGameCommunicationProtocol;
import com.gentlemansoftware.easyServer.EasyRunnableParameters;
import com.gentlemansoftware.easyServer.EasyRunnableParametersInterface;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.helper.MyTextInputListener;
import com.gentlemansoftware.pixelworld.menuComponents.ChatOverlay;
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

	ChatOverlay chatoverlay;
	SimpleMenuRunnableItem connectToLocal, hostLocal, messageItem, disconnectItem;
	private MyTextInputListener inputListener;
	
	public MultiplayerMenu(MenuHandler handler, Menu parent) {
		super(handler, parent, "Multiplayer", null);
		inputListener = new MyTextInputListener(createRunnableSendMessage(), "Connect to IP", "",
				"Ip Adress");
		this.setContent(initMenuComponents());
		userIsNotConnected();
	}
	
	private EasyRunnableParametersInterface<String> createRunnableSendMessage() {
		EasyRunnableParametersInterface<String> aRunnable = new EasyRunnableParameters<String>() {
			public void run() {
				String message = this.getParam();
				handler.user.network.connectTo(message);
			}
		};

		return aRunnable;
	}

	public void userIsConnected() {
		this.addContent(disconnectItem);
		this.addContent(messageItem);
		this.removeContent(hostLocal);
		this.removeContent(connectToLocal);
	}

	public void userIsNotConnected() {
		this.removeContent(disconnectItem);
		this.removeContent(messageItem);
		this.addContent(hostLocal);
		this.addContent(connectToLocal);
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();

		Runnable connectRunnable = new Runnable() {
			public void run() {
				inputListener.getInput();
			}
		};

		connectToLocal = new SimpleMenuRunnableItem("Connect", SimpleMenuNameTypes.SUB, connectRunnable);

		Runnable hostRunnable = new Runnable() {
			public void run() {
				handler.user.network.hostServer();
			}
		};

		hostLocal = new SimpleMenuRunnableItem("Host", SimpleMenuNameTypes.SUB, hostRunnable);

		Runnable messageRunnable = new Runnable() {
			public void run() {
				handler.user.network.gameClient.waitForTextInputAndSendMessage();
			}
		};

		messageItem = new SimpleMenuRunnableItem("Message", SimpleMenuNameTypes.SUB, messageRunnable);

		Runnable disconnectRunnable = new Runnable() {
			public void run() {
				handler.user.network.disconnect();
			}
		};

		disconnectItem = new SimpleMenuRunnableItem("Disconnect", SimpleMenuNameTypes.SUB, disconnectRunnable);

		chatoverlay = new ChatOverlay(this.handler);
		this.addNoChainContent(chatoverlay);

		menuComponents.add(parent);

		return menuComponents;
	}

}
