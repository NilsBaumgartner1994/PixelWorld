package com.gof.simplemenu;

import com.gof.game.CameraControllerInterface;
import com.gof.inputs.GamePad;

public interface SimpleMenuComponent {

	public boolean update(GamePad gamepad);
	public int render(CameraControllerInterface display, int ypos);
	public void select();
	public void setActive(boolean b);
	public void dispose();

}
