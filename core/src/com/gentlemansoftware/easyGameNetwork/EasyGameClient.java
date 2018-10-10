package com.gentlemansoftware.easyGameNetwork;

import com.gentlemansoftware.easyServer.EasyClient;
import com.gentlemansoftware.easyServer.EasyClientInterface;
import com.gentlemansoftware.easyServer.EasyRunnableParameters;
import com.gentlemansoftware.easyServer.EasyRunnableParametersInterface;
import com.gentlemansoftware.easyServer.EasyServerHelpers;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.helper.MyTextInputListener;
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
		client = new EasyClient(this);
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
		if (client != null)
			client.close();
	}

	@Override
	public void connectionEstablished() {
		// TODO Auto-generated method stub
		user.menuHandler.mainMenu.multiplayerMenu.userIsConnected();
	}

	@Override
	public void connectionLost(String message) {
		// TODO Auto-generated method stub
		network.addLogMessage(message);
		user.menuHandler.mainMenu.multiplayerMenu.userIsNotConnected();
	}

	@Override
	public void messageReceived(String message) {
		decompileReceivedMessage(message);
	}

	@Override
	public boolean isConnected() {
		return client.isConnected();
	}
	
	private void decompileReceivedMessage(String message){
		Main.log(getClass(), "Decompiling Message");
		EasyGameCommunicationProtocol protocol = EasyGameCommunicationProtocol.received(message);
		Main.log(getClass(), "Message decompiled null?: "+(protocol==null));
		if(protocol.messageReq!=null){
			network.addLogMessage(protocol.messageReq.message);
		}
		if(protocol.chunkReq!=null){
			Main.log(getClass(), "Received a Chunk Request Response with length: "+message.length());
			Chunk c = protocol.chunkReq.chunk;
			c.setTransients(gameWorld);
			this.gameWorld.setChunk(c);
		}
	}

}