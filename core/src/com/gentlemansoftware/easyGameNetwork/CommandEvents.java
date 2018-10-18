package com.gentlemansoftware.easyGameNetwork;

import com.gentlemansoftware.easyGameNetworkProtocol.EasyGameCommunicationProtocol;
import com.gentlemansoftware.easyGameNetworkProtocol.GameProtocolEntity;
import com.gentlemansoftware.easyServer.EasyConnectionToClient;
import com.gentlemansoftware.pixelworld.entitys.Bat;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.entitys.Human;
import com.gentlemansoftware.pixelworld.helper.ArrayHelper;
import com.gentlemansoftware.pixelworld.physics.Position;

public class CommandEvents {

	private final static String CMD = "/";

	public static boolean isCommand(String message) {
		if (message == null)
			return false;
		return (message.startsWith(CMD));
	}

	public static void handleCommand(EasyGameServer server, EasyConnectionToClient client, String message) {
		String command = message.substring(1);
		String[] params = command.split(" ");
		if (params.length >= 1) {
			switch (params[0]) {
			case "spawn":
				handleSpawnCommand(server, client, params);
			}
		}
	}

	public static void handleSpawnCommand(EasyGameServer server, EasyConnectionToClient client, String[] params) {
		if (params.length == 3) {
			String entity = params[1];
			String pos = params[2];
			if (pos.startsWith("{") && pos.endsWith("}")) {
				String coordsS = pos.substring(1, pos.length() - 1);
				String[] coords = coordsS.split(",");
				if (coords.length == 6) {
					try {
						int[] coord = ArrayHelper.stringArrToIntArr(coords);
						Position p = new Position(coord[0], coord[1], coord[2], coord[3], coord[4], coord[5]);
						Entity e = null;
						if(entity.equals(Human.class.getSimpleName())){
							e = new Human(server.gameWorld,p,"Bob");
						}
						if(entity.equals(Bat.class.getSimpleName())){
							e = new Bat(server.gameWorld,p);
						}
						if(e!=null){
							e.spawn();
							server.gameWorld.entityhandler.registerEntity(e);
							
							EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
							GameProtocolEntity entityProto = new GameProtocolEntity();
							entityProto.spawn = true;
							entityProto.position = p;
							entityProto.uuid = e.getUUID();
							entityProto.entityClass = e.getClass().getName();
							entityProto.ownEntity = false;
							protocol.addGameProtocolEntity(entityProto);
							server.sendGameProtocolToAll(protocol);
						} else {
							client.sendMessage("No Such Entity Type found");
						}
					} catch (Exception e) {
						client.sendMessage("Invalid Params for Command");
						return;
					}
				}
			}
		} else {
			client.sendMessage("Not Enough Parameters");
		}
	}

}