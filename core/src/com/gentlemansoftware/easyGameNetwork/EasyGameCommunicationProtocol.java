package com.gentlemansoftware.easyGameNetwork;

import com.badlogic.gdx.utils.Json;
import com.gentlemansoftware.pixelworld.world.Chunk;

public class EasyGameCommunicationProtocol {
	
	public GameProtocolChunkRequest chunkReq;
	public GameProtocolMessage messageReq;
	
	public EasyGameCommunicationProtocol(){
		
	}
	
	public static EasyGameCommunicationProtocol received(String text){
		Json json = new Json();
		EasyGameCommunicationProtocol protocol = json.fromJson(EasyGameCommunicationProtocol.class, text);
		return protocol;
	}
	
	public static String sendChunkRequest(int cx, int cy){
		EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
		GameProtocolChunkRequest mess = new GameProtocolChunkRequest();
		mess.cx = cx;
		mess.cy = cy;
		protocol.chunkReq = mess;
		Json json = new Json();
		return json.toJson(protocol);
	}
	
	public static String sendChunkRequest(int cx, int cy, Chunk c){
		EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
		GameProtocolChunkRequest mess = new GameProtocolChunkRequest();
		mess.cx = cx;
		mess.cy = cy;
		mess.chunk = c;
		protocol.chunkReq = mess;
		Json json = new Json();
		return json.toJson(protocol);
	}
	
	public static String sendMessage(String message){
		EasyGameCommunicationProtocol protocol = new EasyGameCommunicationProtocol();
		GameProtocolMessage mess = new GameProtocolMessage();
		mess.setMessage(message);
		protocol.messageReq = mess;
		Json json = new Json();
		return json.toJson(protocol);
	}
	
	
	
}