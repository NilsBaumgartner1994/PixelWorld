package com.gentlemansoftware.pixelworld.nature;

import com.gentlemansoftware.pixelworld.materials.MyMaterial;

public interface GrowableInterface {

	public void nextGrowState();
	public int getGrowState();
	public void setGrowState(int pos);
	
}
