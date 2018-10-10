package com.gentlemansoftware.easyGameNetwork;

import com.gentlemansoftware.easyServer.EasyConnectionToClient;
import com.gentlemansoftware.easyServer.EasyServer;
import com.gentlemansoftware.easyServer.EasyServerHelpers;
import com.gentlemansoftware.easyServer.EasyServerInterface;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.world.Chunk;
import com.gentlemansoftware.pixelworld.world.TileWorld;

public class EasyGameServer implements EasyServerInterface {

	public EasyServer server;
//	EasyGameNetwork network;
	public TileWorld gameWorld;
	public EasyGameLogMessages logMessages;

	public EasyGameServer(EasyGameNetwork network) {
		this(network.logMessages);
	}

	public EasyGameServer(EasyGameLogMessages logMessages) {
		this.logMessages = logMessages;
		gameWorld = new TileWorld("Default");
		server = new EasyServer(EasyServerHelpers.getLocalHost(), this);
	}

	public void stopServer() {
		if (isAlive()) {
			server.close();
		}
	}

	public boolean isAlive() {
		return server.isAlive();
	}

	public void startServer() {
		server.start();
	}

	@Override
	public void newConnection(EasyConnectionToClient client) {
		logMessages.addLogMessage(client.clientNumber + ": " + "joined");
		String protocol = EasyGameCommunicationProtocol.sendMessage(client.clientNumber + ": " + "joined");
		server.sendMessageToAll(protocol);
	}

	@Override
	public void clientLeft(EasyConnectionToClient client) {
		logMessages.addLogMessage(client.clientNumber + ": " + "left");
		String protocol = EasyGameCommunicationProtocol.sendMessage(client.clientNumber + ": " + "left");
		server.sendMessageToAll(protocol);
	}

	@Override
	public void messageReceived(EasyConnectionToClient client, String message) {
		decompileReceivedMessage(client,message);
	}

	@Override
	public void connectionLost(EasyConnectionToClient client, String message) {
		logMessages.addLogMessage(client.clientNumber + ": " + message);
		String protocol = EasyGameCommunicationProtocol.sendMessage(client.clientNumber + ": " + message);
		server.sendMessageToAll(protocol);
	}

	@Override
	public void makeUpdates() {
		// TODO Auto-generated method stub

	}
	
	private void decompileReceivedMessage(EasyConnectionToClient client, String message){
		EasyGameCommunicationProtocol protocol = EasyGameCommunicationProtocol.received(message);
//		network.addLogMessage(message);
		if(protocol.messageReq!=null){
			String logMessage = client.clientNumber + ": "+protocol.messageReq.message;
			logMessages.addLogMessage(logMessage);
			String answer = EasyGameCommunicationProtocol.sendMessage(logMessage);
			server.sendMessageToAll(answer);
		}
		if(protocol.chunkReq!=null){
			int cx = protocol.chunkReq.cx;
			int cy = protocol.chunkReq.cy;
			Chunk c = this.gameWorld.getChunk(cx, cy);
			String answer = EasyGameCommunicationProtocol.sendChunkRequest(cx, cy, c);
			server.sendMessageTo(client, answer);
		}
		
	}

}