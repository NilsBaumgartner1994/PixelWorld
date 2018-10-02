package com.gentlemansoftware.easyServer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EasyServerCommunicationReceive {

	private static final Logger LOGGER = java.util.logging.Logger.getLogger(EasyServerCommunicationReceive.class.getName());
	
	private List<Object[]> loggedMessages;
	private List<EasyRunnableParametersInterface<TYPE>> functions;
	
	public static enum TYPE {
		SETTINGUP, WAITING, ESTABLISHED, LOST, CLOSED, MESSAGE, ERROR
	}

	public EasyServerCommunicationReceive() {
		LOGGER.log(Level.INFO, "Create Functions List");
		loggedMessages = new LinkedList<Object[]>();
		
		functions = new ArrayList<EasyRunnableParametersInterface<TYPE>>();
		for(TYPE typ : TYPE.values()) {
			functions.add(null);
		}
		LOGGER.log(Level.INFO, "Createted Functions List", new Object[]{functions});
	}
	
	public List<Object[]> getLogMessages(){
		return this.loggedMessages;
	}

	private void addLogMessage(TYPE type, Object information){
		Object[] message = new Object[2];
		message[0] = type;
		message[1] = information;
		this.loggedMessages.add(message);
	}

	public void setCallbackRunnable(TYPE function, EasyRunnableParametersInterface<TYPE> runnable) {
		LOGGER.log(Level.INFO, "setCallbackRunnable", new Object[]{function,runnable});
		functions.set(function.ordinal(), runnable);
	}

	public void callback(TYPE function, Object param) {
		LOGGER.log(Level.INFO, "executing callback", new Object[]{function,param});
		EasyRunnableParametersInterface<TYPE> runnable = functions.get(function.ordinal());
		if (runnable != null) {
			runnable.setParam(param);
			runnable.run();
		}
	}

	public void connectionSettingUp(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionSettingUp", new Object[]{optionalMessage});
		addLogMessage(TYPE.SETTINGUP,optionalMessage);
		callback(TYPE.SETTINGUP, optionalMessage);
	}

	public void connectionWaiting(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionWaiting", new Object[]{optionalMessage});
		addLogMessage(TYPE.WAITING,optionalMessage);
		callback(TYPE.WAITING, optionalMessage);
	}

	public void connectionEstablished(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionEstablished", new Object[]{optionalMessage});
		addLogMessage(TYPE.ESTABLISHED,optionalMessage);
		callback(TYPE.ESTABLISHED, optionalMessage);
	}

	public void connectionLost(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionLost", new Object[]{optionalMessage});
		addLogMessage(TYPE.LOST,optionalMessage);
		callback(TYPE.LOST, optionalMessage);
	}

	public void connectionClosed(String optionalMessage) {
		LOGGER.log(Level.INFO, "connectionClosed", new Object[]{optionalMessage});
		addLogMessage(TYPE.CLOSED,optionalMessage);
		callback(TYPE.CLOSED, optionalMessage);
	}

	public void receiveMessage(String message) {
		LOGGER.log(Level.INFO, "receiveMessage", new Object[]{message});
		addLogMessage(TYPE.MESSAGE,message);
		callback(TYPE.MESSAGE, message);
	}

	public void error(String message) {
		LOGGER.log(Level.INFO, "error", new Object[]{message});
		addLogMessage(TYPE.ERROR,message);
		callback(TYPE.ERROR, message);
	}

}