package com.gentlemenssoftware.easyServer;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;

public class EasyServerListGoogleSheet implements EasyServerListInterface {

	EasyGoogleSheetsHandler handler;

	private static final String masterServerListSpreadSheetId = "1SkstzWkc2wZ-1iT0hSsgB2ADbxaWED1PRgCZBA8HF6U";
	private static final String readingArea = "A6:F";

	private EasyServerInformationInterface connectedServer;
	private EasyServerCommunicationReceive receive;
	
	public EasyServerListGoogleSheet(EasyServerCommunicationReceive receive){
		this.receive = receive;
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

	        public void run() {
	            System.out.println("noooo i wanna live !");
	            unregisterServer();
	        }
	    }));
	}

	@Override
	public String getServiceName() {
		return "Google";
	}

	@Override
	public boolean connectToMasterServer() {
		System.out.println("Connection To Master Server");
		handler = new EasyGoogleSheetsHandler(masterServerListSpreadSheetId);
		System.out.println("EasyGoogleSheetsHandler set up succesfully: " + handler.validSetup());
		return handler.validSetup();
	}

	@Override
	public boolean isConnectionToMasterServerEstablished() {
		if (handler == null)
			return false;
		return handler.validSetup();
	}

	@Override
	public boolean connectToMasterServerIfNotConnected() {
		if (isConnectionToMasterServerEstablished()) {
			return true;
		} else {
			return connectToMasterServer();
		}
	}

	@Override
	public int getAmountServers() {
		connectToMasterServerIfNotConnected();
		return Integer.parseInt(handler.readCell("D2"));
	}

	@Override
	public Runnable createRunnable(final RUNFUNCTION func) {

		Runnable aRunnable = new Runnable() {
			public void run() {
				switch (func) {
				case CONNECTTOMASTERSERVER : connectToMasterServer(); break;
				case DISCONNECTFROMMASTERSERVER : disconnectFromMasterServer(); break;
				case REGISTERSERVER:
					registerServer(receive);
					break;
				case UNREGISTERSERVER:
					unregisterServer();
					break;
				}
			}
		};

		return aRunnable;

	}

	@Override
	public List<EasyServerInformationInterface> getServers() {
		connectToMasterServerIfNotConnected();
		List<EasyServerInformationInterface> servers = new ArrayList<EasyServerInformationInterface>();

		List<List<Object>> serversObjectList = handler.readCells(readingArea);
		
		if(serversObjectList==null) return servers;
		for (List<Object> serverObject : serversObjectList) {
			EasyServerInformation server = parseGoogleSheetServerObjectListToServer(serverObject);
			if (server != null)
				servers.add(server);
		}

		return servers;
	}

	private EasyServerInformation parseGoogleSheetServerObjectListToServer(List<Object> serverRow) {
		if (serverRow.size() >= 5) {
			String rowNumber = "" + serverRow.get(0);
			String displayName = "" + serverRow.get(1);
			String ip = "" + serverRow.get(2);
			String port = "" + serverRow.get(3);
			String updateTime = "" + serverRow.get(4);
			String owner = "" + serverRow.get(5);

			return new EasyServerInformation(rowNumber, displayName, ip,port, updateTime, owner);
		}
		return null;
	}

	private String[] parseServerToGoogleSheetServerObjectList(EasyServerInformationInterface server) {
		return new String[] { server.getUniqueID(), server.getDisplayName(), server.getIP(), server.getPort(), server.getUpdateTime(),
				server.getOwner() };
	}

	@Override
	public EasyServerCommunicationSend registerServer(EasyServerCommunicationReceive receive) {
		connectToMasterServerIfNotConnected();
		String displayName = EasyServerHelpers.getHostname();
		String ip = EasyServerHelpers.getOwnIP();
		String port = EasyServerHelpers.getPort();
		String updateTime = EasyServerHelpers.getTimeNow();
		String owner = EasyServerHelpers.getUsername();
		
		EasyServerInformationInterface server = new EasyServerInformation(null, displayName, ip,port, updateTime, owner);
		return registerServer(server, receive);
	}

	@Override
	public EasyServerCommunicationSend registerServer(EasyServerInformationInterface server, EasyServerCommunicationReceive receive) {
		connectToMasterServerIfNotConnected();

		EasyServerCommunicationSend send = server.setupServer(receive);
		
		if(!send.isValidSetup()) {
			return null;
		}
		EasyGoogleUpdateAction updateAction = handler.addInRandomRowData(parseServerToGoogleSheetServerObjectList(server));
		if (updateAction == null)
			return null;
		BatchUpdateValuesResponse response = updateAction.getResponse();
		if (response == null)
			return null;
		server.setUniqueID(updateAction.getRow());
		return send;
	}

	@Override
	public void disconnectFromMasterServer() {
		handler = null;
	}
	
	@Override
	public boolean unregisterServer() {
		if (isConnectedToAServer()) {
			return unregisterServer(this.connectedServer);
		}

		return true;
	}

	@Override
	public boolean unregisterServer(EasyServerInformationInterface server) {
		connectToMasterServerIfNotConnected();

		BatchUpdateValuesResponse response = handler.writeIntoRow("B" + server.getUniqueID(), new String[] { "", "" , "", "", "" });
		
		if(response!=null){
			this.connectedServer = null;
		}
		return response!=null;
	}

	@Override
	public boolean isConnectedToAServer() {
		return this.connectedServer != null;
	}

	@Override
	public EasyServerCommunicationSend connectTo(EasyServerInformationInterface server) {
		this.connectedServer = server;
		return server.connectToServerAsClient(receive);
	}

}