package com.gentlemansoftware.pixelworld.simplemenu;

import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.inputs.GamePad;

public interface SimpleMenuComponent {

	public boolean update(GamePad gamepad);
	public int render(CameraControllerInterface display, int ypos);
	public void select();
	public void setActive(boolean b);
	public void dispose();

}
