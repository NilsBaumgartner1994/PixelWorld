package com.gof.menu;

import com.gof.game.CameraControllerInterface;
import com.gof.simplemenu.SimpleMenuComponent;

public interface Menu extends SimpleMenuComponent{

	public void render(CameraControllerInterface display);
	
}
