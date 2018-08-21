package com.gof.menu;

import com.gof.Inputs.GamePad;
import com.gof.game.CameraController;

public interface Menu {
	public void update(GamePad gamepad);
	public void render(CameraController display);
}
