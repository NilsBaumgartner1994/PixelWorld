package com.gentlemansoftware.pixelworld.menu;

import java.util.LinkedList;
import java.util.List;

import com.gentlemansoftware.easyServer.EasyRunnableParameters;
import com.gentlemansoftware.easyServer.EasyRunnableParametersInterface;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.profiles.UserProfile;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenu;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuNameTypes;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuRunnableItem;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuStringEditable;
import com.gentlemansoftware.pixelworld.world.WorldToPNG;

public class UserProfileMenu extends SimpleMenu {

	UserProfile profile;
	
	public UserProfileMenu(MenuHandler handler, UserProfileListMenu parent, UserProfile profile) {
		super(handler, parent, profile.name.getVar(), null);
		this.setType(SimpleMenuNameTypes.SUB);
		
		this.profile = profile;
		
		this.setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();

		SimpleMenuStringEditable name = new SimpleMenuStringEditable(profile.name);
		menuComponents.add(name);
		
		name.setChangeCallback(createRunnableEdit());
		
		SimpleMenuStringEditable uuid = new SimpleMenuStringEditable(profile.uuid);
		menuComponents.add(uuid);
		
		Runnable deleteRunnable = new Runnable() {
			public void run() {
				handler.setActivMenu(parent);
				profile.delete();
				UserProfileListMenu p = (UserProfileListMenu) parent;
				p.updateComponents();
			}
		};

		SimpleMenuRunnableItem deleteItem = new SimpleMenuRunnableItem("Delete", SimpleMenuNameTypes.MAIN,
				deleteRunnable);
		
		menuComponents.add(deleteItem);

		menuComponents.add(parent);

		return menuComponents;
	}
	
	private void update(){
		this.setTitle(profile.name.getVar());
	}
	
	private EasyRunnableParametersInterface<String> createRunnableEdit() {
		EasyRunnableParametersInterface<String> aRunnable = new EasyRunnableParameters<String>() {
			public void run() {
				profile.save();
				update();
			}
		};

		return aRunnable;
	}

}
