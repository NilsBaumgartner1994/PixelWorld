package com.gentlemansoftware.easyServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class EasyServerCommunicationSend {

	private EasyServerCommunicationSendThread send;
	private Thread thread;

	public EasyServerCommunicationSend(EasyServerInformationInterface server, EasyServerCommunicationTyp typ,
			EasyServerCommunicationReceive callback) {
		send = new EasyServerCommunicationSendThread(server, typ, callback);
		if (isValidSetup()) {
			thread = new Thread(send);
			thread.start();
		}
	}

	public boolean sendMessage(String message) {
		return send.sendMessage(message);
	}
	
	public void closeConnection(){
		send.closeConnection();
	}
	
	public boolean isConnectedToAServer(){
		return send.getHoldConnection();
	}

	public boolean isValidSetup() {
		return send.isValidSetup();
	}

}