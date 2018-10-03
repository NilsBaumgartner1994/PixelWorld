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

public class EasyCommunicationConnectionToServer implements Runnable {

	private Socket socket;
	private EasyServerInformationInterface serverInformation;
	BufferedWriter bw;
	BufferedReader br;
	int clientNumber;
	EasyServerCommunicationReceive callback;
	boolean holdConnection;
	boolean connected;

	Thread ownThread;

	public EasyCommunicationConnectionToServer(EasyServerInformationInterface serverInformation,
			EasyServerCommunicationReceive callback) {
		this.connected = false;
		this.serverInformation = serverInformation;
		this.callback = callback;
		this.serverInformation = serverInformation;
		this.holdConnection = true;

		callback.connectionSettingUp("Setting up");
		this.socket = setupSocket();
		createReaderAndWriter();
		this.connected = true;
		this.callback.connectionEstablished("Connected to Server");

		ownThread = new Thread(this);
		ownThread.start();
	}

	public Socket setupSocket() {
		try {
			return new Socket(serverInformation.getIP(), Integer.parseInt(serverInformation.getPort()));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
				callback.receiveMessage(anfrage);
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