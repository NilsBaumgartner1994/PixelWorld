package com.gentlemansoftware.pixelworld.worldgenerator;

import java.io.Serializable;

import com.gentlemansoftware.pixelworld.world.TileWorld;

public interface GeneratorInterface extends Serializable {

	public void generateChunkAt(int cx, int cy);
	public void setTileWorld(TileWorld world);
	
}