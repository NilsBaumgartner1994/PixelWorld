package com.gof.materials;

public class Grass extends Material {

	public static final String INNER = "grass";
	public static final String GRASS_SAND_STRAIGHT = "grass-sand";
	public static final String GRASS__SAND_CORNER_OUTER = "grass-sand-outer";
	public static final String GRASS__SAND_CORNER_INNER = "grass-sand-inner";

	public Grass() {
		setTexture(INNER);
	}

}