package com.gof.menu;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.gof.Inputs.GamePad;
import com.gof.Inputs.GamePadButtons;

public interface Menu {
	public void update(GamePad gamepad);
	public void render();
}
