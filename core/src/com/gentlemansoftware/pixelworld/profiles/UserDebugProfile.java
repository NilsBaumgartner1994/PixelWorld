package com.gentlemansoftware.pixelworld.profiles;

import java.io.Serializable;

public class UserDebugProfile implements Serializable,UserVariableProfileInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207593096419342158L;
	
	public VarHolder<Boolean> showDebugInformationSide;
	public VarHolder<Boolean> showCoordinatesOnMapTiles;
	public VarHolder<Boolean> showMapTilesDrawOrder;
	
	public UserDebugProfile(){
		this.showDebugInformationSide = new VarHolder<Boolean>(false,"Side Information");
		this.showCoordinatesOnMapTiles = new VarHolder<Boolean>(false,"Map Coordinates");
		this.showMapTilesDrawOrder = new VarHolder<Boolean>(false,"Draw Order");
	}
	
	@Override
	public VarHolder<?>[] getVars(){
		return new VarHolder[]{showDebugInformationSide,showCoordinatesOnMapTiles,showMapTilesDrawOrder};
	}
	
	
	
}
