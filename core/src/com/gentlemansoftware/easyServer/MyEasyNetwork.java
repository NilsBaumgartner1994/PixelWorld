package com.gentlemansoftware.easyServer;

import com.badlogic.gdx.Gdx;
import com.gentlemansoftware.easyServer.EasyServerCommunicationReceive.TYPE;
import com.gentlemansoftware.pixelworld.helper.MyTextInputListener;

public class MyEasyNetwork {

	private EasyServerCommunicationReceive receive;
	private EasyServerCommunicationSend connection;

	public MyEasyNetwork() {
		receive = new EasyServerCommunicationReceive();
		EasyRunnableParametersInterface<TYPE> erp = createRunnableReceiveMessage();
		receive.setCallbackRunnable(TYPE.MESSAGE, erp);
		receive.setCallbackRunnable(TYPE.ESTABLISHED, createRunnableEstblished());
	}

	public void sendMessage() {
		if (connection != null && connection.isValidSetup()) {
			MyTextInputListener listener = new MyTextInputListener(connection);
			Gdx.input.getTextInput(listener, "Dialog Title", "Initial Textfield Value", "Hint Value");
		}
	}

	private EasyRunnableParametersInterface<TYPE> createRunnableReceiveMessage() {
		EasyRunnableParametersInterface<EasyServerCommunicationReceive.TYPE> aRunnable = new EasyRunnableParameters<EasyServerCommunicationReceive.TYPE>() {
			public void run() {
				System.out.println(""+this.getParam().toString());
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