package com.gentlemansoftware.easyServer;

public interface EasyServerInterface {

	public void newConnection(EasyConnectionToClient client);
	
	public void connectionLost(EasyConnectionToClient client, String message);
	
	public void makeUpdates();

	public void clientLeft(EasyConnectionToClient client);
	
	public void messageReceived(EasyConnectionToClient client, String message);

}