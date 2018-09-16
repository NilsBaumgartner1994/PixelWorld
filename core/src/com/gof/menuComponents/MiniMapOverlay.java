package com.gof.menuComponents;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gof.game.CameraControllerInterface;
import com.gof.helper.SpriteHelper;
import com.gof.inputs.GamePad;
import com.gof.menu.MenuHandler;
import com.gof.simplemenu.SimpleMenuComponent;
import com.gof.world.WorldToPNG;

public class MiniMapOverlay implements SimpleMenuComponent {

	private MenuHandler handler;
	private Sprite map;

	public MiniMapOverlay(MenuHandler handler) {
		this.handler = handler;
	}

	private final int WIDTH = 128;

	public void updateMap() {
		Pixmap pixmap = WorldToPNG.getPixmap(this.handler.user.human.getMapTile().chunk);
		this.map = new Sprite(new Texture(pixmap));
		this.map = SpriteHelper.setToWidth(map, WIDTH);
		pixmap.dispose();
	}

	@Override
	public boolean update(GamePad gamepad) {
		return false;
	}

	@Override
	public int render(CameraControllerInterface display, int ypos) {
		updateMap();
		if (this.map != null) {
			int x = display.getWidth()-WIDTH;
			int y = display.getHeigth();

			map.setPosition(x, y);
			display.drawSpriteAndSubtractYpos(map, x, y);
		}
		return 0;
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActive(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
