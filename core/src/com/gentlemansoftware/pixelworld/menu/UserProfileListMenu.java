package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;

import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.profiles.UserProfile;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuNameTypes;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuRunnableItem;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuVarholder;
import com.gentlemansoftware.pixelworld.world.WorldToPNG;

public class UserProfileListMenu extends SimpleMenu {
	
	UserProfileListMenu instance;

	public UserProfileListMenu(MenuHandler handler, Menu parent) {
		super(handler, parent, "User Profiles", null);
		instance = this;
		
		this.setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();

		List<UserProfile> profiles = UserProfile.getAllUserProfiles();
		
		Runnable createRunnable = new Runnable() {
			public void run() {
				UserProfile profile = new UserProfile();
				UserProfileMenu profileItem = new UserProfileMenu(handler,instance,profile);
				instance.removeContent(parent);
				instance.addContent(profileItem);
				instance.addContent(parent);
			}
		};
		
		SimpleMenuRunnableItem createItem = new SimpleMenuRunnableItem("New", SimpleMenuNameTypes.MAIN,
				createRunnable);
		menuComponents.add(createItem);
		
		
		for(UserProfile profile : profiles){
			UserProfileMenu profileItem = new UserProfileMenu(handler,this,profile);
			menuComponents.add(profileItem);
		}

		menuComponents.add(parent);

		return menuComponents;
	}
	
	public void updateComponents(){
		this.setContent(initMenuComponents());
	}

}
