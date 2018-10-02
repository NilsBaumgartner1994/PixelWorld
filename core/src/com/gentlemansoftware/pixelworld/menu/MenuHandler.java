package com.gentlemansoftware.pixelworld.menu;

import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.profiles.User;

public class MenuHandler {

	public Menu activMenu;
	public Menu ingameMenu, pauseMenu, mainMenu;
	
	public User user;
	
	public MenuHandler(User user) {
		this.user = user;
		initMenus();
		this.setActivMenu(mainMenu);
	}
	
	public void initMenus(){
		mainMenu = new MainMenu(this,null);
		ingameMenu = new Ingame(this,pauseMenu);
		pauseMenu = new PauseMenu(this,ingameMenu);
	}
	
	public void setActivMenu(Menu menu){
		menu.prepareForActivation();
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
