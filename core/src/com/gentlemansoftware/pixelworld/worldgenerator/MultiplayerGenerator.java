package com.gentlemansoftware.pixelworld.worldgenerator;

import java.util.Random;

import com.gentlemansoftware.easyGameNetwork.EasyGameClient;
import com.gentlemansoftware.easyGameNetwork.EasyGameCommunicationProtocol;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.world.Chunk;
import com.gentlemansoftware.pixelworld.world.TileWorld;

public class MultiplayerGenerator implements GeneratorInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7778561222591949339L;

	TileWorld world;
	private transient EasyGameClient client;

	public MultiplayerGenerator() {

	}
	
	public MultiplayerGenerator setGameClient(EasyGameClient client){
		this.client = client;
		return this;
	}
	
	@Override
	public void setTileWorld(TileWorld world){
		this.world = world;
	}

	@Override
	public void generateChunkAt(int cx, int cy) {
		Main.log(getClass(), "Chunk(" + cx + "|" + cy + "): generating");
		Chunk c = new Chunk(this.world,cx,cy);
		world.setChunk(c);
		Main.log(getClass(), "Chunk(" + cx + "|" + cy + "): noise");
		String proto = EasyGameCommunicationProtocol.sendChunkRequest(cx, cy);
		client.sendMessage(proto);
		Main.log(getClass(), "Chunk(" + cx + "|" + cy + "): just blank first");
	}

}