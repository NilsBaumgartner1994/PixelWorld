package com.gentlemansoftware.easyGameNetwork;

import java.util.LinkedList;
import java.util.List;

public class EasyGameLogMessages {

	public List<Object> logMessages;
	
	public EasyGameLogMessages(){
		logMessages = new LinkedList<Object>();
	}
	
	public List<Object> getLogMessages() {
		return this.logMessages;
	}
	
	public void addLogMessage(String message){
		this.logMessages.add(message);
	}

}