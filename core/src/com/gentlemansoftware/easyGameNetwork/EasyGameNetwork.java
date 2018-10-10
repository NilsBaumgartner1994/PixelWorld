package com.gentlemansoftware.easyGameNetwork;

import java.util.LinkedList;
import java.util.List;

import com.gentlemansoftware.easyServer.EasyServerHelpers;
import com.gentlemansoftware.pixelworld.profiles.User;

public class EasyGameNetwork {

	public EasyGameServer gameServer;
	public EasyGameClient gameClient;
	private User user;
	public List<Object> logMessages;

	public EasyGameNetwork(User user) {
		this.user = user;
		logMessages = new LinkedList<Object>();
		this.gameServer = new EasyGameServer(this,user);
		this.gameClient = new EasyGameClient(this,user);
	}

	public List<Object> getLogMessages() {
		return this.logMessages;
	}
	
	public void addLogMessage(String message){
		this.logMessages.add(message);
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