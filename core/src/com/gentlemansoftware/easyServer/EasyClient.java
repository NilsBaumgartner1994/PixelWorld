package com.gentlemansoftware.easyServer;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class EasyClient implements EasyConnectionInterface, Runnable {

	private EasyServerInformationInterface serverInformation;

	private EasyConnection connection;
	boolean connected;
	EasyClientInterface callback;
	private Thread ownThread;
	int tickRate = 1000/10;
	public EasyClientInformation clientInf;

	public EasyClient(EasyClientInterface callback, String username) {
		this.callback = callback;
		this.connected = false;
		this.clientInf = new EasyClientInformation();
		this.clientInf.name = username;
	}

	public void connectToServer(String ip) {
		connectToServer(EasyServerHelpers.getDefaultServerInformationForIP(ip));
	}

	public void connectToServer(EasyServerInformationInterface serverInformation) {
		this.serverInformation = serverInformation;
		connectToServer();
	}

	public void connectToServer() {
		Socket s = setupSocket();
		if (s != null) {
			this.connection = new EasyConnection(s, this);
			this.connected = true;
			callback.connectionEstablished();
			ownThread = new Thread(this);
			ownThread.start();
		}
	}

	public boolean isConnected() {
		return this.connected;
	}

	public Socket setupSocket() {
		try {
			return new Socket(serverInformation.getIP(), Integer.parseInt(serverInformation.getPort()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void receiveMessage(String anfrage) {
		callback.messageReceived(anfrage);
	}

	@Override
	public void connectionLost(String message) {
		this.connected = false;
		callback.connectionLost(message);
	}

	@Override
	public boolean sendMessage(String message) {
		return connection.sendMessage(message);
	}

	@Override
	public void close() {
		this.connected = false;
		connection.close();
		try {
			ownThread.join(1000L, 0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ownThread = null;
	}

	public void sendUpdates() {
		callback.sendUpdates();
	}

	@Override
	public void run() {
		while (this.isConnected()) {
			try {
				Thread.sleep(tickRate);
				sendUpdates();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}