package com.gentlemansoftware.easyGameNetworkProtocol;

import com.badlogic.gdx.utils.Json;
import com.gentlemansoftware.easyServer.EasyClientInformation;
import com.gentlemansoftware.pixelworld.world.Chunk;

public class EasyGameCommunicationProtocol {

	public GameProtocolChunkRequest chunkReq;
	public GameProtocolMessage messageReq;
	public GameProtocolEntity entityProtocol;
	public EasyClientInformation clientInf;

	public EasyGameCommunicationProtocol() {
	}

	public void addGameProtocolEntity(GameProtocolEntity entityProto) {
		// if(this.entityProtocols==null){
		// entityProtocols = new ArrayList<GameProtocolEntity>();
		// }
		// this.entityProtocols.add(entityProto);
		this.entityProtocol = entityProto;
	}

	public static String sendChunkRequest(int cx, int cy) {
		EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
		GameProtocolChunkRequest mess = new GameProtocolChunkRequest();
		mess.setX(cx);
		mess.setY(cy);
		protocol.chunkReq = mess;
		Json json = new Json();
		return json.toJson(protocol);
	}

	public static String sendChunkRequest(int cx, int cy, Chunk c) {
		EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
		GameProtocolChunkRequest mess = new GameProtocolChunkRequest();
		mess.cx = cx;
		mess.cy = cy;
		mess.chunk = c;
		protocol.chunkReq = mess;
		Json json = new Json();
		return json.toJson(protocol);
	}

	public static String sendMessage(String message) {
		EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
		GameProtocolMessage mess = new GameProtocolMessage();
		mess.setMessage(message);
		protocol.messageReq = mess;
		Json json = new Json();
		return json.toJson(protocol);
	}
	
	public String getJsonString(){
		Json json = new Json();
		return json.toJson(this);
	}

}