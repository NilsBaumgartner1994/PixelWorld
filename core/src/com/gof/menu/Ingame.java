package com.gof.menu;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.gof.Inputs.GamePad;
import com.gof.Inputs.GamePadButtons;
import com.gof.entitys.Human;

public class Ingame implements Menu {

	public MenuHandler menuHandler;

	public Ingame(MenuHandler menuHandler) {
		this.menuHandler = menuHandler;
	}

	@Override
	public void update(GamePad gamepad) {
		if(gamepad.getButton(GamePadButtons.ESC).isPressed()){
			menuHandler.setActivMenu(menuHandler.pauseMenu);
		}
		Human human = this.menuHandler.user.human;
		human.setLeftStick(gamepad.getLeftStick());
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
