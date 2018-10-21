package com.gentlemansoftware.pixelworld.profiles;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GameServerProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4049832344375766584L;

	public VarHolder<String> serverName;
	public VarHolder<Boolean> test;
	public VarHolder<Integer> maxPlayers;
	public VarHolder<Integer> tickRate;

	public GameServerProfile() {
		initVariables();
	}

	private void initVariables() {
		this.maxPlayers = new VarHolder<Integer>(32, "Max Players");
		this.tickRate = new VarHolder<Integer>(10, "Updates/Second");
		this.serverName = new VarHolder<String>("Test Server", "Server Name");
		this.test = new VarHolder<Boolean>(true,"Test");
	}
	
	public String getName(){
		return "Game Server Options";
	}

	public List<VarHolder<? extends Serializable>> getVars() {
		List<VarHolder<?>> list = new LinkedList<VarHolder<?>>();
//		list.add(maxPlayers);
//		list.add(tickRate);
		list.add(serverName);
		list.add(test);
		return list;
	}

}
