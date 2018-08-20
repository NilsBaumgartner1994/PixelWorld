package com.gof.menu;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.gof.Inputs.GamePad;
import com.gof.entitys.Human;
import com.gof.profiles.User;

public class MenuHandler {

	public Menu activMenu;
	public Ingame ingameMenu;
	public PauseMenu pauseMenu;
	
	public User user;
	
	public MenuHandler(User user) {
		this.user = user;
		initMenus();
		this.setActivMenu(ingameMenu);
	}
	
	public void initMenus(){
		ingameMenu = new Ingame(this);
		pauseMenu = new PauseMenu(this);
	}
	
	public void setActivMenu(Menu menu){
		this.activMenu = menu;
	}

	public void renderActivMenu() {
		if (activMenu != null) {
			activMenu.render();
		}
	}

	public Menu getActivMenu() {
		return this.activMenu;
	}
	
	public void updateInput(GamePad gamepad){
		if(this.activMenu!=null){
			this.activMenu.update(gamepad);
		}
	}

}
