package com.gentlemansoftware.pixelworld.game;

import java.util.LinkedList;
import java.util.List;

import com.gentlemansoftware.pixelworld.world.TileWorld;

public class TileWorldEventHandler {

	private List<String> loggedMessages;
	private TileWorld world;

	public TileWorldEventHandler(TileWorld world) {
		this.loggedMessages = new LinkedList<String>();
		this.world = world;
	}

	private void log(String message) {
		loggedMessages.add(message);
	}



}
