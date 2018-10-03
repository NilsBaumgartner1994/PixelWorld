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

public class EasyCommunicationConnectionToClient implements Runnable {

	private Socket socket;
	BufferedWriter bw;
	BufferedReader br;
	int clientNumber;
	EasyServerCommunicationReceive callback;
	boolean holdConnection;
	EasyCommunicationServer server;
	
	Thread ownThread;

	public EasyCommunicationConnectionToClient(EasyCommunicationServer server, Socket socket, int clientNumber, EasyServerCommunicationReceive callback) {
		this.server = server;
		this.socket = socket;
		this.holdConnection = true;
		this.callback = callback;
		this.clientNumber = clientNumber;
		createReaderAndWriter();
		
		ownThread = new Thread(this);
		ownThread.start();
	}

	public boolean sendMessage(String message) {
		try {
			bw.write(message);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public void run() {
		String anfrage;

		try {
			while (holdConnection) {
				anfrage = br.readLine();
				if(anfrage!=null){
					callback.receiveMessage("Client "+clientNumber+": "+anfrage);
				} else {
					callback.receiveMessage("Client "+clientNumber+": left");
					callback.connectionLost("Client "+clientNumber+" left unexpected cause sending null?");
					holdConnection = false;
				}
			}

			bw.close();
			br.close();
			socket.close();
		} catch (IOException e) {
			callback.connectionLost("Client left unexpected ?");
		}
	}

	private boolean createReaderAndWriter() {
		if (socket == null)
			return false;

		try {
			bw = getWriter(socket.getOutputStream());
			br = getReader(socket.getInputStream());

			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private BufferedReader getReader(InputStream socketinstr) {
		InputStreamReader isr = new InputStreamReader(socketinstr);
		return new BufferedReader(isr);
	}

	private BufferedWriter getWriter(OutputStream socketoutstr) {
		OutputStreamWriter osr = new OutputStreamWriter(socketoutstr);
		return new BufferedWriter(osr);
	}

}