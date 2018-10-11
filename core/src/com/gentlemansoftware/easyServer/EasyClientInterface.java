package com.gentlemansoftware.easyServer;

public interface EasyClientInterface {

	public void connectionEstablished();

	public void connectionLost(String message);

	public void messageReceived(String message);
	
	public boolean isConnected();
	
	public void sendUpdates();

}