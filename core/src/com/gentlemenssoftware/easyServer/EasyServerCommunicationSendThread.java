package com.gentlemenssoftware.easyServer;

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

public class EasyServerCommunicationSendThread implements Runnable {

	private EasyServerInformationInterface server;
	private EasyServerCommunicationTyp typ;
	private EasyServerCommunicationReceive callback;

	public EasyServerCommunicationSendThread(EasyServerInformationInterface server, EasyServerCommunicationTyp typ,
			EasyServerCommunicationReceive callback) {
		this.server = server;
		this.callback = callback;
		this.typ = typ;
		validSetup = false;
		connectionClosed();
		if (isHost()) {
			setupAsHost();
		} else if (isClient()) {
			setupAsClient();
		}
	}

	private boolean holdConnection;
	private boolean connectionClosed;
	private boolean validSetup;

	public void closeConnection() {
		connectionClosed();
		holdConnection = false;
	}

	private void openConnection() {
		if (isValidSetup()) {
			if (isHost()) {
				startAsHost();
			} else if (isClient()) {
				startAsClient();
			}
		}
	}

	@Override
	public void run() {
		if (connectionClosed) {
			connectionClosed = false;
			holdConnection = true;
			openConnection();
		}
	}

	private void connectionClosed() {
		this.connectionClosed = true;
	}

	private boolean isHost() {
		return this.typ == EasyServerCommunicationTyp.HOST;
	}

	private boolean isClient() {
		return this.typ == EasyServerCommunicationTyp.CLIENT;
	}

	BufferedWriter bw;
	BufferedReader br;

	public boolean sendOnlyAndNotToMe(String message) {
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
	
	private String getMessagePrefix() {
		String prefix = "Unknown";
		
		if(isHost()) {
			prefix = EasyServerCommunicationTyp.HOST.toString();
		}
		if(isClient()) {
			prefix = EasyServerCommunicationTyp.CLIENT.toString();
		}
		return prefix+": ";
	}
	
	public boolean sendMessage(String message) {
		message = getMessagePrefix()+message;
		
		boolean success = sendOnlyAndNotToMe(message);
		recieveMessage(message);
		
		return success;
	}

	private void recieveMessage(String message) {
		callback.receiveMessage(message);
	}

	private Socket socket;
	private ServerSocket serverSocket;

	private void setupAsHost() {
		callback.connectionSettingUp("Setting up Connection");
		try {
			serverSocket = new ServerSocket(Integer.parseInt(server.getPort()));
			this.validSetup = true;
			callback.connectionSettingUp("Settup valid");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.validSetup = false;
			callback.error("Settup invalid!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.validSetup = false;
			callback.error("Settup invalid!");
		}
	}

	public boolean isValidSetup() {
		return this.validSetup;
	}

	private void startAsHost() {
		try {
			callback.connectionWaiting("Waiting for a Connection");
			socket = serverSocket.accept();
			callback.connectionEstablished("Found a Connection");

			boolean created = createReaderAndWriter(socket);
			if (created) {
				String anfrage;
				String antwort;

				while (holdConnection) {
					anfrage = br.readLine();
					recieveMessage(anfrage);
				}
				closeConnection();
			} else {
				callback.error("Could not create Reader and Writer ! Please try again !");
			}

			bw.close();
			br.close();
			socket.close();
			serverSocket.close();

			connectionClosed();
		} catch (UnknownHostException uhe) {
			System.out.println(uhe);
		} catch (BindException ex) {
			System.out.println(ex);
		} catch (ConnectException ex) {
			System.out.println(ex);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	private void startAsClient() {
		try {
			callback.connectionSettingUp("Setting up Connection");
			socket = new Socket(server.getIP(), Integer.parseInt(server.getPort()));
			callback.connectionEstablished("Found a Connection");

			boolean created = createReaderAndWriter(socket);
			if (created) {
				String antwort;

				while (holdConnection) {
					antwort = br.readLine();
					recieveMessage(antwort);
				}
				closeConnection();
			} else {
				callback.error("Could not create Reader and Writer ! Please try again !");
			}

			bw.close();
			br.close();
			socket.close();

			connectionClosed();
		} catch (UnknownHostException uhe) {
			System.out.println(uhe);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	private void setupAsClient() {
		this.validSetup = true;
		// Clients have nothing to do at the moment
	}

	private boolean createReaderAndWriter(Socket socket) {
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