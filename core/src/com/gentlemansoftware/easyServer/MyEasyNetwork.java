package com.gentlemansoftware.easyServer;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.Gdx;
import com.gentlemansoftware.easyServer.EasyServerCommunicationReceive.TYPE;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.helper.MyTextInputListener;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.profiles.User;

public class MyEasyNetwork {

	private EasyCommunicationServer server;
	private EasyCommunicationConnectionToServer client;

	EasyServerCommunicationReceive callback;

	private MyTextInputListener sendListener;
	private User user;

	public MyEasyNetwork(User user) {
		this.user = user;
		callback = new EasyServerCommunicationReceive();
		callback.setCallbackRunnable(TYPE.ESTABLISHED, createRunnableEstblished());
		sendListener = new MyTextInputListener(createRunnableSendMessage(), "Dialog Title", "Initial Value",
				"Hint Value");
	}

	public List<Object[]> getLogMessages() {
		if (callback == null) {
			return new LinkedList<Object[]>();
		}
		return callback.getLogMessages();
	}

	public void sendMessage() {
		sendListener.getInput();
	}

	public void disconnect() {
		if (client != null)
			client.holdConnection = false;
		if(server!=null)
			server.close();
		
		user.menuHandler.mainMenu.multiplayerMenu.userIsNotConnected();
	}

	private EasyRunnableParametersInterface createRunnableSendMessage() {
		EasyRunnableParametersInterface aRunnable = new EasyRunnableParameters() {
			public void run() {
				if (client != null && client.connected) {
					client.sendMessage((String) this.getParam());
				}
			}
		};

		return aRunnable;
	}

	private EasyRunnableParametersInterface<TYPE> createRunnableReceiveMessage() {
		EasyRunnableParametersInterface<EasyServerCommunicationReceive.TYPE> aRunnable = new EasyRunnableParameters<EasyServerCommunicationReceive.TYPE>() {
			public void run() {
				String totalMessage = this.getParam().toString();
				System.out.println("" + totalMessage);
			}
		};

		return aRunnable;
	}
	
	private EasyRunnableParametersInterface<TYPE> createRunnableServerReceiveMessage() {
		EasyRunnableParametersInterface<EasyServerCommunicationReceive.TYPE> aRunnable = new EasyRunnableParameters<EasyServerCommunicationReceive.TYPE>() {
			public void run() {
				if(server!=null){
					server.sendMessageToAll(this.getParam().toString());
				}
			}
		};

		return aRunnable;
	}

	private EasyRunnableParametersInterface<TYPE> createRunnableEstblished() {
		EasyRunnableParametersInterface<EasyServerCommunicationReceive.TYPE> aRunnable = new EasyRunnableParameters<EasyServerCommunicationReceive.TYPE>() {
			public void run() {
				System.out.println("Connection found");
			}
		};

		return aRunnable;
	}

	public void connectToLocalServer() {
		connectTo(EasyServerHelpers.getOwnIP());
	}

	public void connectTo(String ip) {
		EasyRunnableParametersInterface<TYPE> erp = createRunnableReceiveMessage();
		callback.setCallbackRunnable(TYPE.MESSAGE, erp);
		
		client = new EasyCommunicationConnectionToServer(EasyServerHelpers.getDefaultServerInformationForIP(ip),
				callback);
		
		user.menuHandler.mainMenu.multiplayerMenu.userIsConnected();
	}

	public void hostServer() {
		EasyRunnableParametersInterface<TYPE> erp = createRunnableServerReceiveMessage();
		callback.setCallbackRunnable(TYPE.MESSAGE, erp);
		
		server = new EasyCommunicationServer(EasyServerHelpers.getLocalHost(), callback);
		
		
		user.menuHandler.mainMenu.multiplayerMenu.userIsConnected();
	}

}