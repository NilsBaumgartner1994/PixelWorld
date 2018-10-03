package com.gentlemansoftware.easyServer;
public interface EasyServerInformationInterface {
	
	public String getDisplayName();
	public String getIP();
	public String getPort();
	public String getUpdateTime();
	public String getOwner();
	public String getUniqueID();
	public void setDisplayName(String displayName);
	public void setIP(String ip);
	public void setUpdateTime(String updateTime);
	public void setOwner(String owner);
	public void setUniqueID(String uniqueID);
	public void setPort(String port);
}