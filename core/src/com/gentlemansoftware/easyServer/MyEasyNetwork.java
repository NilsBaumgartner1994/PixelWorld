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

	private EasyServerCommunicationReceive receive;
	private EasyServerCommunicationSend connection;
	private MyTextInputListener sendListener;
	private User user;

	public MyEasyNetwork(User user) {
		this.user = user;
		receive = new EasyServerCommunicationReceive();
		EasyRunnableParametersInterface<TYPE> erp = createRunnableReceiveMessage();
		receive.setCallbackRunnable(TYPE.MESSAGE, erp);
		receive.setCallbackRunnable(TYPE.ESTABLISHED, createRunnableEstblished());
		sendListener = new MyTextInputListener(createRunnableSendMessage(), "Dialog Title", "Initial Value",
				"Hint Value");
	}

	public List<Object[]> getLogMessages() {
		if (receive == null) {
			return new LinkedList<Object[]>();
		}
		return receive.getLogMessages();
	}

	public boolean isConnectedToServer() {
		return connection != null && connection.isConnectedToAServer();
	}

	public void sendMessage() {
		sendListener.getInput();
	}

	public void sendGameLogicUpdate(String message) {
		if (connection != null && connection.isValidSetup()) {
			connection.sendMessage(message);
		}
	}

	public void disconnect() {
		connection.closeConnection();
	}

	private EasyRunnableParametersInterface createRunnableSendMessage() {
		EasyRunnableParametersInterface aRunnable = new EasyRunnableParameters() {
			public void run() {
				if (connection != null && connection.isValidSetup()) {
					connection.sendMessage((String) this.getParam());
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
		String displayName = EasyServerHelpers.getHostname();
		String port = EasyServerHelpers.getPort();
		String owner = EasyServerHelpers.getUsername();
		String uniqueID = EasyServerHelpers.getOwnIP();
		String time = EasyServerHelpers.getTimeNow();

		EasyServerInformation serverInformation = new EasyServerInformation(uniqueID, displayName, ip, port, time,
				owner);
		connection = serverInformation.connectToServerAsClient(receive);
	}

	public void hostServer() {
		String displayName = EasyServerHelpers.getHostname();
		String port = EasyServerHelpers.getPort();
		String owner = EasyServerHelpers.getUsername();
		String ip = EasyServerHelpers.getOwnIP();
		String uniqueID = EasyServerHelpers.getOwnIP();
		String time = EasyServerHelpers.getTimeNow();

		EasyServerInformation serverInformation = new EasyServerInformation(uniqueID, displayName, ip, port, time,
				owner);
		connection = serverInformation.setupServer(receive);
	}

}