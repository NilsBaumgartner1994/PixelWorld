package com.gentlemansoftware.easyGameNetwork;

import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.utils.Json;
import com.gentlemansoftware.easyGameNetworkProtocol.EasyGameCommunicationProtocol;
import com.gentlemansoftware.easyGameNetworkProtocol.GameProtocolEntity;
import com.gentlemansoftware.easyServer.EasyConnectionToClient;
import com.gentlemansoftware.easyServer.EasyServer;
import com.gentlemansoftware.easyServer.EasyServerHelpers;
import com.gentlemansoftware.easyServer.EasyServerInterface;
import com.gentlemansoftware.pixelworld.entitys.Bat;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.world.Chunk;
import com.gentlemansoftware.pixelworld.world.TileWorld;

public class EasyGameServer implements EasyServerInterface {

	public EasyServer server;
	// EasyGameNetwork network;
	public TileWorld gameWorld;
	public EasyGameLogMessages logMessages;
	public HashMap<EasyConnectionToClient, Entity> clientEntitys;
	User user;

	public EasyGameServer(EasyGameNetwork network) {
		this.user = network.user;
		this.logMessages = network.logMessages;
		init();
	}

	public EasyGameServer(EasyGameLogMessages logMessages) {
		this.logMessages = logMessages;
		init();
	}

	private void init() {
		gameWorld = new TileWorld("Default");
		clientEntitys = new HashMap<EasyConnectionToClient, Entity>();
		server = new EasyServer(EasyServerHelpers.getLocalHost(), this);
	}

	public void stopServer() {
		if (isAlive()) {
			server.close();
		}
		if (user != null) {
			user.menuHandler.mainMenu.multiplayerMenu.userIsNotConnected();
			user.menuHandler.mainMenu.removeContent(user.menuHandler.ingameMenu);
			user.menuHandler.setActivMenu(user.menuHandler.mainMenu);
		}
	}

	public boolean isAlive() {
		return server.isAlive();
	}

	public void startServer() {
		server.start();
		if (user != null) {
			user.menuHandler.mainMenu.multiplayerMenu.userIsConnected();
			user.menuHandler.mainMenu.addContent(user.menuHandler.ingameMenu);
			user.menuHandler.setActivMenu(user.menuHandler.ingameMenu);
		}
	}

	@Override
	public void newConnection(EasyConnectionToClient client) {
		logMessages.addLogMessage(client.clientInf.getClientID() + ": " + "joined");
		String protocol = EasyGameCommunicationProtocol.sendMessage(client.clientInf.getClientID() + ": " + "joined");
		server.sendMessageToAll(protocol);

		PixelWorldGameServerEvents.newConnection(this, client);
	}

	public void sendGameProtocolTo(EasyGameCommunicationProtocol proto, EasyConnectionToClient client) {
		Json json = new Json();
		String message = json.toJson(proto);
		server.sendMessageTo(client, message);
	}
	
	public void sendGameProtocolToAll(EasyGameCommunicationProtocol protocol){
		for (EasyConnectionToClient client : this.getAllClients()) {
			this.sendGameProtocolTo(protocol, client);
		}
	}

	@Override
	public void clientLeft(EasyConnectionToClient client) {
		logMessages.addLogMessage(client.clientInf.getClientID() + ": " + "left");
		String protocol = EasyGameCommunicationProtocol.sendMessage(client.clientInf.getClientID() + ": " + "left");
		server.sendMessageToAll(protocol);

		PixelWorldGameServerEvents.clientLeft(this, client);
	}

	@Override
	public void messageReceived(EasyConnectionToClient client, String message) {
		decompileReceivedMessage(client, message);
	}

	@Override
	public void connectionLost(EasyConnectionToClient client, String message) {
		clientLeft(client);
	}

	@Override
	public void sendUpdates() {
		EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
		for (Entity e : gameWorld.entityhandler.getAllEntitys()) {
			GameProtocolEntity entityProto = new GameProtocolEntity();
			entityProto.setPosition = true;
			entityProto.position = e.getPosition();
			entityProto.uuid = e.getUUID();
			protocol.addGameProtocolEntity(entityProto);

			sendGameProtocolToAll(protocol);
		}
	}

	private void decompileReceivedMessage(EasyConnectionToClient client, String message) {
		Json json = new Json();
		EasyGameCommunicationProtocol protocol = json.fromJson(EasyGameCommunicationProtocol.class, message);
		if (protocol.clientInf != null) {
//			Main.log(getClass(), "Received request for ClientInformation");
			client.clientInf.name = protocol.clientInf.name;
			client.clientInf.nameAccepted = true;

			EasyGameCommunicationProtocol answer = new EasyGameCommunicationProtocol();
			answer.clientInf = client.clientInf;

			sendGameProtocolTo(answer, client);
		}
		if (protocol.messageReq != null) {
			String logMessage = client.clientInf.getClientID() + ": " + protocol.messageReq.message;
			logMessages.addLogMessage(logMessage);
			if(CommandEvents.isCommand(protocol.messageReq.message)){
				CommandEvents.handleCommand(this, client, protocol.messageReq.message);
			} else {
				String answer = EasyGameCommunicationProtocol.sendMessage(logMessage);
				server.sendMessageToAll(answer);
			}
		}
		if (protocol.chunkReq != null) {
			int cx = protocol.chunkReq.cx;
			int cy = protocol.chunkReq.cy;
			Chunk c = this.gameWorld.getChunk(cx, cy);
			String answer = EasyGameCommunicationProtocol.sendChunkRequest(cx, cy, c);
			server.sendMessageTo(client, answer);
		}
		if (protocol.entityProtocol != null) {
			// for (GameProtocolEntity prot : protocol.entityProtocols) {
			GameProtocolEntity prot = protocol.entityProtocol;
			if (prot.setPosition) {
				// Main.log(getClass(), "Receiving Positionupdate");
				String uuid = prot.uuid;
				Position p = prot.position;
				Entity e = gameWorld.entityhandler.getEntity(uuid);
				e.setPosition(p);
			}
			// }
		}

	}

	@Override
	public List<EasyConnectionToClient> getAllClients() {
		return server.getAllClients();
	}

}