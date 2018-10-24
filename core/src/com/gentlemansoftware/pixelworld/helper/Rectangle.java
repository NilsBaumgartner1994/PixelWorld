package com.gentlemansoftware.pixelworld.helper;

import com.gentlemansoftware.pixelworld.game.Main;

public class Rectangle {

	public int x, y, width, height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public String toString() {
		return "x:"+x + " y:" + y + " w:" + width + " h:" + height;
	}

	public boolean isInRegion(int x, int y) {
		return (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height);
	}

}
