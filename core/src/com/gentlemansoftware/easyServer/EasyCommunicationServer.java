package com.gentlemansoftware.easyServer;

import java.util.LinkedList;
import java.util.List;

public class EasyCommunicationServer implements Runnable {

	List<EasyCommunicationConnectionToClient> clients;
	boolean alive = true;
	int clientNumber = 0;

	EasyServerInformationInterface serverInformation;
	EasyServerCommunicationReceive callback;
	EasyCommunicationServerConnectionListener connectionListener;
	Thread ownThread;

	public EasyCommunicationServer(EasyServerInformationInterface serverInformation,
			EasyServerCommunicationReceive callback) {
		this.serverInformation = serverInformation;
		this.callback = callback;
		this.clients = new LinkedList<EasyCommunicationConnectionToClient>();
		
		this.connectionListener = new EasyCommunicationServerConnectionListener(this);
		
		ownThread = new Thread(this);
		ownThread.start();
	}
	
	public void close(){
		this.connectionListener.close();
		this.alive = false;
		try {
			ownThread.join(1000L,0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void newConnection(EasyCommunicationConnectionToClient client) {
		clients.add(client);
		callback.connectionEstablished("Client "+client.clientNumber+" has joint");
		sendMessageToAll("Client "+client.clientNumber+" has joint");
	}
	
	public void clientLeft(EasyCommunicationConnectionToClient client){
		clients.remove(client);
		sendMessageToAll("Client "+client.clientNumber+" has left");
	}

	public void sendMessageToAll(String message) {
		for (EasyCommunicationConnectionToClient client : clients) {
			client.sendMessage(message);
		}
	}

	@Override
	public void run() {
		
	}

}