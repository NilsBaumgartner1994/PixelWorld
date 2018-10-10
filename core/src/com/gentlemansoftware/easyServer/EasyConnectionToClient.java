package com.gentlemansoftware.easyServer;

import java.net.Socket;

public class EasyConnectionToClient implements EasyConnectionInterface {

	public int clientNumber;
	boolean holdConnection;
	EasyConnection connection;
	EasyServer server;
	
	Thread ownThread;

	public EasyConnectionToClient(EasyServer server, Socket socket, int clientNumber) {
		this.server = server;
		this.holdConnection = true;
		this.clientNumber = clientNumber;
		
		connection = new EasyConnection(socket,this);
	}

	public boolean sendMessage(String message) {
		return connection.sendMessage(message);
	}

	@Override
	public void receiveMessage(String anfrage) {
		server.messageReceived(this, anfrage);
	}

	@Override
	public void connectionLost(String message) {
		server.connectionLost(this,message);
	}

	@Override
	public void close() {
		connection.close();
	}

}