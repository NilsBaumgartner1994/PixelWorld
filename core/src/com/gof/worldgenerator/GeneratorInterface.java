package com.gof.worldgenerator;

import java.io.Serializable;

import com.gof.world.TileWorld;

public interface GeneratorInterface extends Serializable {

	public void generateChunkAt(int cx, int cy);
	public void setTileWorld(TileWorld world);
	
}