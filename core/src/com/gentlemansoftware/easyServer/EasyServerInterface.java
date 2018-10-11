package com.gentlemansoftware.easyServer;

import java.util.List;

public interface EasyServerInterface {

	public void newConnection(EasyConnectionToClient client);
	
	public void connectionLost(EasyConnectionToClient client, String message);
	
	public List<EasyConnectionToClient> getAllClients();
	
	public void sendUpdates();

	public void clientLeft(EasyConnectionToClient client);
	
	public void messageReceived(EasyConnectionToClient client, String message);

}