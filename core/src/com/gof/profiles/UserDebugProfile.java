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
		this.showDebugInformationSide = new VarHolder<Boolean>(false);
		this.showDebugInformationCoordinatesOnMapTiles = new VarHolder<Boolean>(false);
	}
	
	
	
}
