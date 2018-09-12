package com.gof.profiles;

import java.io.Serializable;

public class UserDebugProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207593096419342158L;
	
	public VarHolder<Boolean> showDebugInformationSide;
	public VarHolder<Boolean> showDebugInformationCoordinatesOnMapTiles;
	
	public UserDebugProfile(){
		this.showDebugInformationSide = new VarHolder<Boolean>();
		this.showDebugInformationSide.value = false;

		this.showDebugInformationCoordinatesOnMapTiles = new VarHolder<Boolean>();
		this.showDebugInformationCoordinatesOnMapTiles.value = false;
	}
	
	
	
}
