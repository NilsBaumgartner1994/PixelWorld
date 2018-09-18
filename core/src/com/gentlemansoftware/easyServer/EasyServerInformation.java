package com.gentlemansoftware.easyServer;

public class EasyServerInformation implements EasyServerInformationInterface, Comparable<EasyServerInformation>{

	private String displayName;
	private String ip;
	private String port;
	private String updateTime;
	private String owner;
	private String uniqueID;
	
	public EasyServerInformation(String uniqueID, String displayName, String ip, String port,String updateTime, String owner) {
		this.setDisplayName(displayName);
		this.setIP(ip);
		this.setPort(port);
		this.setOwner(owner);
		this.setUniqueID(uniqueID);
		this.setUpdateTime(updateTime);
	}

	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	@Override
	public String getIP() {
		return this.ip;
	}
	
	@Override
	public String getPort(){
		return this.port;
	}

	@Override
	public String getUpdateTime() {
		return this.updateTime;
	}

	@Override
	public String getOwner() {
		return this.owner;
	}

	@Override
	public String getUniqueID() {
		return this.uniqueID;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public void setIP(String ip) {
		this.ip = ip;
	}
	
	@Override
	public void setPort(String port){
		this.port = port;
	}

	@Override
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	@Override
	public int compareTo(EasyServerInformation o) {
		return this.getUniqueID().compareTo(o.getUniqueID());
	}

	@Override
	public EasyServerCommunicationSend connectToServerAsClient(EasyServerCommunicationReceive receive) {
		return new EasyServerCommunicationSend(this,EasyServerCommunicationTyp.CLIENT, receive);
	}

	@Override
	public EasyServerCommunicationSend setupServer(EasyServerCommunicationReceive receive) {
		return new EasyServerCommunicationSend(this,EasyServerCommunicationTyp.HOST, receive);
	}
	

}