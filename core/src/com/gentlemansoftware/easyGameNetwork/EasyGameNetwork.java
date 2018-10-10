package com.gentlemansoftware.easyGameNetwork;

import java.util.List;

import com.gentlemansoftware.easyServer.EasyServerHelpers;
import com.gentlemansoftware.pixelworld.profiles.User;

public class EasyGameNetwork {

	public EasyGameServer gameServer;
	public EasyGameClient gameClient;
	private User user;
	public EasyGameLogMessages logMessages;

	public EasyGameNetwork(User user) {
		this.user = user;
		logMessages = new EasyGameLogMessages();
		this.gameServer = new EasyGameServer(this);
		this.gameClient = new EasyGameClient(this,user);
	}

	public List<Object> getLogMessages() {
		return logMessages.getLogMessages();
	}
	
	public void addLogMessage(String message){
		this.logMessages.addLogMessage(message);
	}

	public void disconnect() {
		gameClient.quit();
		gameServer.stopServer();

		user.menuHandler.mainMenu.multiplayerMenu.userIsNotConnected();
	}

	public void connectToLocalServer() {
		connectTo(EasyServerHelpers.getOwnIP());
	}

	public void connectTo(String ip) {
		gameClient.connectTo(ip);
	}

	public void hostServer() {
		this.gameServer.startServer();
	}

}