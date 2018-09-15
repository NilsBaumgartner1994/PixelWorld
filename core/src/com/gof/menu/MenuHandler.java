package com.gof.menu;

import com.gof.inputs.GamePad;
import com.gof.game.CameraControllerInterface;
import com.gof.profiles.User;

public class MenuHandler {

	public Menu activMenu;
	public Menu ingameMenu, pauseMenu;
	
	public User user;
	
	public MenuHandler(User user) {
		this.user = user;
		initMenus();
		this.setActivMenu(ingameMenu);
	}
	
	public void initMenus(){
		ingameMenu = new Ingame(this,pauseMenu);
		pauseMenu = new PauseMenu(this,ingameMenu);
	}
	
	public void setActivMenu(Menu menu){
		this.activMenu = menu;
	}

	public void renderActivMenu(CameraControllerInterface display) {
		if (activMenu != null) {
			activMenu.render(display);
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
