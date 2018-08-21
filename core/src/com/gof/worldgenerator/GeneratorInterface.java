package com.gof.worldgenerator;

import java.io.Serializable;

public interface GeneratorInterface extends Serializable {

	public void generateChunkAt(int cx, int cy);
	
}