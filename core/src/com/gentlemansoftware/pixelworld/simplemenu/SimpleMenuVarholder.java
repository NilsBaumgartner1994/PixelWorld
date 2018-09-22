package com.gentlemansoftware.pixelworld.simplemenu;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.inputs.GamePadButtons;
import com.gentlemansoftware.pixelworld.menu.Menu;
import com.gentlemansoftware.pixelworld.menu.MenuHandler;
import com.gentlemansoftware.pixelworld.profiles.UserProfile;
import com.gentlemansoftware.pixelworld.profiles.VarHolder;
import com.gentlemansoftware.pixelworld.world.WorldToPNG;

public class SimpleMenuVarholder extends SimpleMenu {

	public SimpleMenuVarholder(MenuHandler handler, Menu parent, String title, VarHolder[] vars) {
		super(handler, parent, title, null);
		this.setContent(initMenuComponents(vars));
	}

	public List<SimpleMenuComponent> initMenuComponents(VarHolder[] vars) {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();
		menuComponents.addAll(SimpleMenuVarHolderComponentHelper.getRightComponents(vars));
		menuComponents.add(parent);

		return menuComponents;
	}

}
