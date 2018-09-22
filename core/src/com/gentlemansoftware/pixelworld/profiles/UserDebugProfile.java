package com.gentlemansoftware.pixelworld.profiles;

public class UserDebugProfile extends UserVariableProfile {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4049832344375766584L;
	
	public VarHolder<Boolean> showDebugInformationSide;
	public VarHolder<Boolean> showCoordinatesOnMapTiles;
	public VarHolder<Boolean> showMapTilesDrawOrder;
	
	public UserDebugProfile(){
		super("Debug Options");
		initVariables();
	}
	
	private void initVariables(){
		this.showDebugInformationSide = new VarHolder<Boolean>(false,"Side Information");
		this.addVar(showDebugInformationSide);
		this.showCoordinatesOnMapTiles = new VarHolder<Boolean>(false,"Map Coordinates");
		this.addVar(showCoordinatesOnMapTiles);
		this.showMapTilesDrawOrder = new VarHolder<Boolean>(false,"Draw Order");		
		this.addVar(showMapTilesDrawOrder);
	}
	
}
