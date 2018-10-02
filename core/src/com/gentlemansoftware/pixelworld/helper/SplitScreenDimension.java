package com.gentlemansoftware.pixelworld.helper;

public class SplitScreenDimension {

	public int x, y, width, height;

	public SplitScreenDimension(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public String toString(){
		return x+" "+y+" "+width+" "+height;
	}

}
