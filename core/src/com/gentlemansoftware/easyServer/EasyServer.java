package com.gentlemansoftware.easyServer;

import java.util.LinkedList;
import java.util.List;

public class EasyServer implements Runnable, EasyServerInterface {

	List<EasyConnectionToClient> clients;
	int clientNumber = 0;
	int tickRate = 1000/10;

	EasyServerInformationInterface serverInformation;
	EasyCommunicationServerConnectionListener connectionListener;
	Thread ownThread;
	EasyServerInterface server;

	public EasyServer(EasyServerInformationInterface serverInformation, EasyServerInterface server) {
		this.serverInformation = serverInformation;
		this.clients = new LinkedList<EasyConnectionToClient>();
		this.server = server;
		this.connectionListener = new EasyCommunicationServerConnectionListener(this);
	}

	public void start() {
		ownThread = new Thread(this);
		ownThread.start();
		this.connectionListener.start();
	}

	public boolean isAlive() {
		return ownThread != null && this.connectionListener.alive == true;
	}

	public void close() {
		connectionListener.close();
		try {
			ownThread.join(1000L, 0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ownThread = null;
	}

	@Override
	public void newConnection(EasyConnectionToClient client) {
		clients.add(client);
		server.newConnection(client);
	}

	@Override
	public void clientLeft(EasyConnectionToClient client) {
		clients.remove(client);
		server.clientLeft(client);
	}

	public void sendMessageToAll(String message) {
		for (EasyConnectionToClient client : clients) {
			client.sendMessage(message);
		}
	}

	public void sendMessageTo(EasyConnectionToClient client, String message) {
		client.sendMessage(message);
	}

	@Override
	public void run() {
		while (this.isAlive()) {
			try {
				Thread.sleep(tickRate);
				sendUpdates();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void messageReceived(EasyConnectionToClient client, String message) {
		server.messageReceived(client, message);
	}

	@Override
	public void connectionLost(EasyConnectionToClient client, String message) {
		clients.remove(client);
		server.connectionLost(client, message);
	}

	@Override
	public void sendUpdates() {
		server.sendUpdates();
	}

	@Override
	public List<EasyConnectionToClient> getAllClients() {
		return this.clients;
	}

}