package com.gentlemansoftware.easyServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

public class EasyClient implements EasyConnectionInterface {

	private EasyServerInformationInterface serverInformation;

	Thread ownThread;
	private EasyConnection connection;
	boolean connected;
	EasyClientInterface callback;
	
	public EasyClient(EasyClientInterface callback) {
		this.callback = callback;
		this.connected = false;
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
		if(s!=null){
			this.connection = new EasyConnection(s,this);
			callback.connectionEstablished();
			this.connected = true;
		}
	}
	
	public boolean isConnected(){
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
	}

}