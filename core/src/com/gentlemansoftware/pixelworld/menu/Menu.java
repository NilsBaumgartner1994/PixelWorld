package com.gentlemansoftware.pixelworld.menu;

import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;

public interface Menu extends SimpleMenuComponent{

	public void render(CameraControllerInterface display);
	public void prepareForActivation();
	
}
