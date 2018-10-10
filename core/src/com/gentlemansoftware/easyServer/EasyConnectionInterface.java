package com.gentlemansoftware.easyServer;

public interface EasyConnectionInterface {

	public boolean sendMessage(String message);
	
	public void receiveMessage(String anfrage);

	public void connectionLost(String message);
	
	public void close();

}