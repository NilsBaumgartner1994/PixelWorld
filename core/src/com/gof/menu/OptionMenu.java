package com.gof.menu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.game.CameraControllerInterface;
import com.gof.game.Main;
import com.gof.game.ResourceLoader;
import com.gof.inputs.GamePad;
import com.gof.inputs.GamePadButtons;
import com.gof.physics.Direction;
import com.gof.profiles.UserProfile;
import com.gof.profiles.VarHolder;
import com.gof.world.WorldToPNG;

public class OptionMenu extends SimpleMenu {

	public OptionMenu(MenuHandler menuHandler, Menu parent) {
		super(menuHandler, parent, "Options", null);
		this.setContent(initMenuComponents());
	}

	public List<SimpleMenuComponent> initMenuComponents() {
		List<SimpleMenuComponent> menuComponents = new LinkedList<SimpleMenuComponent>();

		for (VarHolder var : this.handler.user.profile.debugProfile.getVars()) {
			SimpleMenuComponent c = new SimpleMenuBooleanEditable(var);
			menuComponents.add(c);
		}
		
		Runnable quitRunnable = new Runnable() {
			public void run() {
				WorldToPNG.saveToImage(Main.getInstance().titleScreenWorld);
			}
		};
		
		SimpleMenuRunnableItem WorldToPNG = new SimpleMenuRunnableItem("World To PNG", SimpleMenuNameTypes.SUB,quitRunnable);
		menuComponents.add(WorldToPNG);
		
		menuComponents.add(handler.ingameMenu);

		return menuComponents;
	}

}
