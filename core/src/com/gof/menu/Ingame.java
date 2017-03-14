package com.gof.menu;

import com.badlogic.gdx.graphics.g3d.ModelBatch;

public class Ingame implements Menu {

	public MenuHandler menuHandler;

	public Ingame(MenuHandler menuHandler) {
		this.menuHandler = menuHandler;
	}

	@Override
	public void up() {
		// TODO Auto-generated method stub

	}

	@Override
	public void down() {
		// TODO Auto-generated method stub

	}

	@Override
	public void left() {
		// TODO Auto-generated method stub

	}

	@Override
	public void right() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(ModelBatch batch) {
		// TODO Auto-generated method stub
//		batch.begin(menuHandler.p.cameraController.camera);
//		batch.render(GameClass.instances, environment);
//		batch.end();
	}

	@Override
	public void setAsActivMenu() {
		this.menuHandler.activMenu = this;
	}

}
