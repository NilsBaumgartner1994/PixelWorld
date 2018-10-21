package com.gentlemansoftware.easyServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EasyCommunicationServerConnectionListener implements Runnable {

	boolean alive = true;
	int clientNumber;

	EasyServer server;
	ServerSocket listener;
	Thread ownThread;

	public EasyCommunicationServerConnectionListener(EasyServer server) {
		this.server = server;
	}

	public void start() {
		alive = true;
		clientNumber = 0;
		ownThread = new Thread(this);
		ownThread.start();
	}

	public void newConnection(Socket socket) {
		EasyConnectionToClient client = new EasyConnectionToClient(server, socket, clientNumber++);
		server.newConnection(client);
	}

	public void close() {
		this.alive = false;
		closeListener();
		closeThread();
	}

	private void closeListener() {
		try {
			if (listener != null) {
				listener.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void closeThread() {
		if (ownThread != null) {
			try {
				ownThread.join(1000L, 0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		listener = null;
		try {
			listener = new ServerSocket(Integer.parseInt(server.serverInformation.getPort()));
			while (alive) {
				Socket newConnection = listener.accept();
				if (acceptNewConnection()) {
					newConnection(newConnection);
				} else {
					newConnection.close();
				}
			}
		} catch (SocketException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeListener();
		}
	}

	public boolean acceptNewConnection() {
		return server.variables.maxPlayers.getVar() < 0 || server.clients.size() < server.variables.maxPlayers.getVar();
	}

}