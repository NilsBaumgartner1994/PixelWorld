package com.gentlemenssoftware.easyServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EasyServerCommunicationReceive {

	private static final Logger LOGGER = java.util.logging.Logger.getLogger(EasyServerCommunicationReceive.class.getName());
	
	
	public static enum TYPE {
		SETTINGUP, WAITING, ESTABLISHED, LOST, CLOSED, MESSAGE, ERROR
	}

	public EasyServerCommunicationReceive() {
		LOGGER.log(Level.INFO, "Create Functions List");
		functions = new ArrayList<EasyRunnableParameters<TYPE>>();
		for(TYPE typ : TYPE.values()) {
			functions.add(null);
		}
		LOGGER.log(Level.INFO, "Createted Functions List", new Object[]{functions});
	}

	List<EasyRunnableParameters<TYPE>> functions;

	public void setCallbackRunnable(TYPE function, EasyRunnableParameters<TYPE> runnable) {
		LOGGER.log(Level.INFO, "setCallbackRunnable", new Object[]{function,runnable});
		functions.set(function.ordinal(), runnable);
	}

	public void callback(TYPE function, Object param) {
		LOGGER.log(Level.INFO, "executing callback", new Object[]{function,param});
		EasyRunnableParameters<TYPE> runnable = functions.get(function.ordinal());
		if (runnable != null) {
			runnable.setParam(param);
			runnable.setType(function);
			runnable.run();
		}
	}

	public void connectionSettingUp(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionSettingUp", new Object[]{optionalMessage});
		callback(TYPE.SETTINGUP, optionalMessage);
	}

	public void connectionWaiting(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionWaiting", new Object[]{optionalMessage});
		callback(TYPE.WAITING, optionalMessage);
	}

	public void connectionEstablished(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionEstablished", new Object[]{optionalMessage});
		callback(TYPE.ESTABLISHED, optionalMessage);
	}

	public void connectionLost(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionLost", new Object[]{optionalMessage});
		callback(TYPE.LOST, optionalMessage);
	}

	public void connectionClosed(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionClosed", new Object[]{optionalMessage});
		callback(TYPE.CLOSED, optionalMessage);
	}

	public void receiveMessage(String message) {
		LOGGER.log(Level.INFO, "receiveMessage", new Object[]{message});
		callback(TYPE.MESSAGE, message);
	}

	public void error(String message) {
		LOGGER.log(Level.INFO, "error", new Object[]{message});
		callback(TYPE.ERROR, message);
	}

}