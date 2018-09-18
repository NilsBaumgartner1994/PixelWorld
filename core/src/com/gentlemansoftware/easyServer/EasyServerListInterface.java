package com.gentlemansoftware.easyServer;
import java.util.List;

public interface EasyServerListInterface {
	
	public static enum RUNFUNCTION {
		CONNECTTOMASTERSERVER, DISCONNECTFROMMASTERSERVER, REGISTERSERVER, UNREGISTERSERVER
	};

	public EasyServerCommunicationSend connectTo(EasyServerInformationInterface server);
	public boolean connectToMasterServer();
	public void disconnectFromMasterServer();
	public boolean connectToMasterServerIfNotConnected();
	public String getServiceName();
	public boolean isConnectionToMasterServerEstablished();
	public int getAmountServers();
	public List<EasyServerInformationInterface> getServers();	
	public EasyServerCommunicationSend registerServer(EasyServerCommunicationReceive receive);
	public EasyServerCommunicationSend registerServer(EasyServerInformationInterface server,EasyServerCommunicationReceive receive);
	public boolean unregisterServer();
	public boolean unregisterServer(EasyServerInformationInterface server);
	Runnable createRunnable(RUNFUNCTION func);
	public boolean isConnectedToAServer();
}