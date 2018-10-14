package com.gentlemansoftware.easyGameNetwork;

import com.badlogic.gdx.utils.Json;
import com.gentlemansoftware.easyGameNetworkProtocol.EasyGameCommunicationProtocol;
import com.gentlemansoftware.easyGameNetworkProtocol.GameProtocolEntity;
import com.gentlemansoftware.easyServer.EasyConnectionToClient;
import com.gentlemansoftware.pixelworld.entitys.Bat;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.entitys.Human;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.physics.Position;

public class PixelWorldGameServerEvents {

	public static void newConnection(EasyGameServer server, EasyConnectionToClient client) {
		Position startPos = new Position(0, 0, 0, 0, 1, 0);

		Human newPlayer = new Human(server.gameWorld, startPos, "Bob");
		newPlayer.spawn();
		server.gameWorld.entityhandler.registerEntity(newPlayer);
		String newUUID = newPlayer.getUUID();
		Main.log(PixelWorldGameServerEvents.class, "ClientEntitysEmpty?: " + (server.clientEntitys == null));
		server.clientEntitys.put(client, newPlayer);

		EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
		GameProtocolEntity entityProto = new GameProtocolEntity();
		entityProto.spawn = true;
		entityProto.position = startPos;
		entityProto.uuid = newUUID;
		entityProto.entityClass = Human.class.getName();
		entityProto.ownEntity = true;
		protocol.addGameProtocolEntity(entityProto);

		Json json = new Json();
		String message = json.toJson(protocol);
		Main.log(PixelWorldGameServerEvents.class, message);
		server.sendGameProtocolTo(protocol, client);
		entityProto.ownEntity = false;

		for (EasyConnectionToClient otherClient : server.getAllClients()) {
			if (otherClient != client) {
				server.sendGameProtocolTo(protocol, otherClient);
			}
		}

		
		//spawn a following Bat
		Bat bat = new Bat(server.gameWorld, startPos.cpy().addAndSet(1, 0, 0, 0));
		bat.spawn();
		bat.followUUID = newPlayer.getUUID();
		server.gameWorld.entityhandler.registerEntity(bat);

		protocol = new EasyGameCommunicationProtocol();
		entityProto = new GameProtocolEntity();
		entityProto.spawn = true;
		entityProto.entityClass = Bat.class.getName();
		entityProto.followUUID = bat.followUUID;
		entityProto.position = bat.getPosition();
		entityProto.uuid = bat.getUUID();
		entityProto.ownEntity = false;
		protocol.addGameProtocolEntity(entityProto);
		
		message = json.toJson(protocol);
		Main.log(PixelWorldGameServerEvents.class, message);

		for (EasyConnectionToClient otherClient : server.getAllClients()) {
			server.sendGameProtocolTo(protocol, otherClient);
		}
		server.sendGameProtocolTo(protocol, client);
	}

	public static void clientLeft(EasyGameServer server, EasyConnectionToClient client) {
		Entity e = server.clientEntitys.get(client);
		if (e != null) {
			EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
			GameProtocolEntity entityProto = new GameProtocolEntity();
			entityProto.despawning = true;
			entityProto.uuid = e.getUUID();
			for (EasyConnectionToClient otherClient : server.getAllClients()) {
				server.sendGameProtocolTo(protocol, otherClient);
			}
		}
		e.destroy();
	}

}