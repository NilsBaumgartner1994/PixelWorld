package com.gof.Inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Mouse {
	
	public Button left;
	public Button middle;
	public Button right;
	
	public Vector2 pos;
	
	public int scroll;
	
	public Mouse() {
//		Gdx.input.setCursorCatched(true);
		scroll = 0;
		left = new Button();
		middle = new Button();
		right = new Button();
		pos = new Vector2();
	}		
	
	public void updatePosition(int x, int y){
		pos.x = x;
		pos.y = y;
	}
	
}
