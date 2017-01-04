package com.gof.menu;

import com.badlogic.gdx.graphics.g3d.ModelBatch;

public interface Menu {
	public void up();
	public void down();
	public void left();
	public void right();
	public void render(ModelBatch batch);
	public void setAsActivMenu();
}
