package com.gentlemansoftware.pixelworld.profiles;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UserDebugProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4049832344375766584L;

	public VarHolder<Boolean> showDebugInformationSide;
	public VarHolder<Boolean> showCoordinatesOnMapTiles;
	public VarHolder<Boolean> showMapTilesDrawOrder;

	public UserDebugProfile() {
		initVariables();
	}

	private void initVariables() {
		this.showDebugInformationSide = new VarHolder<Boolean>(false, "Side Information");
//		this.addVar(showDebugInformationSide);
		this.showCoordinatesOnMapTiles = new VarHolder<Boolean>(false, "Map Coordinates");
//		this.addVar(showCoordinatesOnMapTiles);
		this.showMapTilesDrawOrder = new VarHolder<Boolean>(false, "Draw Order");
//		this.addVar(showMapTilesDrawOrder);
	}
	
	public String getName(){
		return "Debug Options";
	}

	public List<VarHolder<? extends Serializable>> getVars() {
		List<VarHolder<?>> list = new LinkedList<VarHolder<?>>();
		list.add(showCoordinatesOnMapTiles);
		list.add(showDebugInformationSide);
		list.add(showMapTilesDrawOrder);
		return list;
	}

}
