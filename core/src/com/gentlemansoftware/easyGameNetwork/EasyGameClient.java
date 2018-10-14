package com.gentlemansoftware.easyGameNetwork;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Json;
import com.gentlemansoftware.easyGameNetworkProtocol.EasyGameCommunicationProtocol;
import com.gentlemansoftware.easyGameNetworkProtocol.GameProtocolEntity;
import com.gentlemansoftware.easyServer.EasyClient;
import com.gentlemansoftware.easyServer.EasyClientInterface;
import com.gentlemansoftware.easyServer.EasyRunnableParameters;
import com.gentlemansoftware.easyServer.EasyRunnableParametersInterface;
import com.gentlemansoftware.easyServer.EasyServerHelpers;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.entitys.Human;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.helper.MyTextInputListener;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.world.Chunk;
import com.gentlemansoftware.pixelworld.world.TileWorld;
import com.gentlemansoftware.pixelworld.worldgenerator.MultiplayerGenerator;

public class EasyGameClient implements EasyClientInterface {

	private EasyClient client;
	private User user;
	private MyTextInputListener sendListener;
	EasyGameNetwork network;
	public TileWorld gameWorld;

	public EasyGameClient(EasyGameNetwork network, User user) {
		this.user = user;
		this.network = network;
		this.gameWorld = new TileWorld("Default", new MultiplayerGenerator().setGameClient(this));
		client = new EasyClient(this, EasyServerHelpers.getUsername());
		sendListener = new MyTextInputListener(createRunnableSendMessage(), "Dialog Title", "Initial Value",
				"Hint Value");
	}

	private EasyRunnableParametersInterface<String> createRunnableSendMessage() {
		EasyRunnableParametersInterface<String> aRunnable = new EasyRunnableParameters<String>() {
			public void run() {
				String message = this.getParam();
				String protocol = EasyGameCommunicationProtocol.sendMessage(message);
				sendMessage(protocol);
			}
		};

		return aRunnable;
	}

	public void waitForTextInputAndSendMessage() {
		if (client.isConnected()) {
			sendListener.getInput();
		}
	}

	public void connectToLocalServer() {
		client.connectToServer(EasyServerHelpers.getOwnIP());
	}

	public void connectTo(String ip) {
		client.connectToServer(ip);
	}

	public void sendMessage(String message) {
		if (client != null && client.isConnected()) {
			client.sendMessage(message);
		}
	}

	public void quit() {
		if (client != null && client.isConnected())
			client.close();
		user.menuHandler.setActivMenu(user.menuHandler.mainMenu);
	}

	@Override
	public void connectionEstablished() {
		// TODO Auto-generated method stub
		this.gameWorld = new TileWorld("Default", new MultiplayerGenerator().setGameClient(this));
		user.menuHandler.mainMenu.multiplayerMenu.userIsConnected();
		user.menuHandler.setActivMenu(user.menuHandler.ingameMenu);

		EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
		protocol.clientInf = this.client.clientInf;
		this.sendGameProtocol(protocol);
	}

	@Override
	public void connectionLost(String message) {
		// TODO Auto-generated method stub
		network.addLogMessage(message);
		user.menuHandler.mainMenu.multiplayerMenu.userIsNotConnected();
		user.menuHandler.setActivMenu(user.menuHandler.mainMenu);
	}

	@Override
	public void messageReceived(String message) {
		decompileReceivedMessage(message);
	}

	@Override
	public boolean isConnected() {
		return client.isConnected();
	}

	private void decompileReceivedMessage(final String message) {
		Json json = new Json();
		EasyGameCommunicationProtocol protocol = json.fromJson(EasyGameCommunicationProtocol.class, message);

		if (protocol.clientInf != null) {
//			Main.log(getClass(), "Got answer of ClientInformation");
			client.clientInf = protocol.clientInf;
		}
		if (protocol.messageReq != null) {
			network.addLogMessage(protocol.messageReq.message);
		}
		if (protocol.chunkReq != null) {
			Main.log(getClass(), message);
			Chunk c = protocol.chunkReq.chunk;
			c.setTransients(gameWorld);
			this.gameWorld.setChunk(c);
			for (Entity e : new ArrayList<Entity>(c.entitys)) {
				if (e.getUUID() != null) {
//					Main.log(getClass(), "Chunk: Oh wow, there is an entity with an UUID, have i seen it before?");
					Entity known = gameWorld.entityhandler.getEntity(e.getUUID());
					if (known != null) {
//						Main.log(getClass(), "Chunk: Oh yea I saw this one somewhere before, better update mine");
						known.setPosition(e.getPosition());
						e.destroy();
						known.destroy();
						known.spawn();
						// known.destroy();
						// known.setPosition(e.getPosition());
						// known.spawn();
//						Main.log(getClass(), "Chunk: HumanIDUpdated?: " + known.toString());
					} else {
//						Main.log(getClass(), "Chunk: Hmm seems new to me");
						e.setPosition(e.getPosition());
						e.destroy();
						e.spawn();
						gameWorld.entityhandler.registerEntity(e);
					}
				}
			}
		}
		if (protocol.entityProtocol != null) {
//			Main.log(getClass(), message);
			if(protocol.entityProtocol.setPosition){
				String su = protocol.entityProtocol.uuid;
				Entity e = gameWorld.entityhandler.getEntity(su);
				if(e!=null){
					if(e!=user.human){
						Position p = protocol.entityProtocol.position;
						e.setPosition(p);
					}
				}
			}
			if (protocol.entityProtocol.despawning) {
				String su = protocol.entityProtocol.uuid;
				Entity e = gameWorld.entityhandler.getEntity(su);
				e.destroy();
			} else if (protocol.entityProtocol.spawn) {
				Position p = protocol.entityProtocol.position;
				String su = protocol.entityProtocol.uuid;
				Entity e = gameWorld.entityhandler.getEntity(su);
//				Main.log(getClass(), "Received a Spawn Protocol");
				if (e != null) {
//					Main.log(getClass(), "Found in the entityhandler already a Entity");
					e.setPosition(p);
				} else {
//					Main.log(getClass(), "Okay no Entity found in entity handler");
					e = new Human(gameWorld, p, "Bob");
					e.setUUID(su);
					e.spawn();
					gameWorld.entityhandler.registerEntity(e);
				}
				if (protocol.entityProtocol.ownEntity) {
//					Main.log(getClass(), "Oh wow it is my own entity");
					user.human = (Human) e;
					user.cameraController.setTrack(e);
				}
			}
		}
	}

	@Override
	public void sendUpdates() {
		if (this.isConnected()) {
			if (user.human != null) {
//				Main.log(getClass(), "Sending Update");
				EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
				GameProtocolEntity entityProto = new GameProtocolEntity();
				entityProto.position = user.human.getPosition();
				entityProto.uuid = user.human.getUUID();
				entityProto.setPosition = true;
				protocol.entityProtocol = entityProto;
				sendGameProtocol(protocol);
			}
		}
	}

	public void sendGameProtocol(EasyGameCommunicationProtocol proto) {
		Json json = new Json();
		String message = json.toJson(proto);
		this.sendMessage(message);
	}

}