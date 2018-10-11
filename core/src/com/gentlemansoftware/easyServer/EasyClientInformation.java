package com.gentlemansoftware.easyServer;

public class EasyClientInformation {

	public int clientNumber;
	public boolean nameAccepted;
	public String name;

	public EasyClientInformation() {

	}
	
	public String getClientID(){
		if(nameAccepted){
			return name+"("+clientNumber+")";
		}
		return ""+clientNumber;
	}

}