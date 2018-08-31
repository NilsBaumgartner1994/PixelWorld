package com.gof.menu;

import com.gof.inputs.GamePad;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.CameraControllerInterface;

public interface Menu {

	public void update(GamePad gamepad);
	public void render(CameraControllerInterface display);
	public void select();
	public void changeSelection(Vector2 vec);
	
}
