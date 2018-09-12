package com.gof.menu;

import com.gof.game.CameraControllerInterface;

public interface MenuComponentContent {
	public int render(CameraControllerInterface display, int yposStart);
	public void setActive(boolean active);

}
