package com.gentlemansoftware.easyGameNetwork;

import com.gentlemansoftware.pixelworld.world.Chunk;

public class GameProtocolChunkRequest {

	public int cx;
	public int cy;
	public Chunk chunk;

	public GameProtocolChunkRequest() {
	}

	public void setX(int cx){
		this.cx = cx;
	}
	
	public void setY(int cy){
		this.cy = cy;
	}
	
	public void setChunk(Chunk chunk){
		this.chunk = chunk;
	}

}