package com.gentlemansoftware.pixelworld.inputs;

import com.badlogic.gdx.math.Vector2;

public class Mouse {

	public Button left;
	public Button middle;
	public Button right;

	public Vector2 pos;

	public int scroll;

	public Mouse() {
		// Gdx.input.setCursorCatched(true);
		scroll = 0;
		left = new Button();
		middle = new Button();
		right = new Button();
		pos = new Vector2();
	}

	public void setPos(int x, int y) {
		this.pos.x = x;
		this.pos.y = y;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos.cpy();
	}

	public Vector2 getPos() {
		return this.pos.cpy();
	}

}
