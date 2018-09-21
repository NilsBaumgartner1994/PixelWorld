package com.gentlemansoftware.pixelworld.menuComponents;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.helper.SpriteHelper;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.menu.MenuHandler;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.simplemenu.SimpleMenuComponent;
import com.gentlemansoftware.pixelworld.world.Chunk;
import com.gentlemansoftware.pixelworld.world.MapTile;
import com.gentlemansoftware.pixelworld.world.TileWorld;
import com.gentlemansoftware.pixelworld.world.WorldToPNG;

public class MiniMapOverlay implements SimpleMenuComponent {

	private MenuHandler handler;
	private Sprite map;

	public MiniMapOverlay(MenuHandler handler) {
		this.handler = handler;
	}

	private final int WIDTH = 128;

	public void updateMap() {
		Pixmap pixmap = WorldToPNG.getPixmap(getMapTileCameraIsLookingAt());
		this.map = new Sprite(new Texture(pixmap));
		this.map = SpriteHelper.setToWidth(map, WIDTH);
		pixmap.dispose();
	}

	@Override
	public boolean update(GamePad gamepad) {
		return false;
	}
	
	private MapTile getMapTileCameraIsLookingAt(){
		TileWorld world = this.handler.user.activGameWorld;
		Position camera = this.handler.user.cameraController.getCameraPosition();
		return world.getMapTileFromGlobalPos(camera.x, camera.y);
	}

	@Override
	public int render(CameraControllerInterface display, int ypos) {
		updateMap();
		if (this.map != null) {
			int x = display.getWidth()-WIDTH;
			int y = display.getHeigth();
			
			MapTile tile = getMapTileCameraIsLookingAt();
			int xPixel = WIDTH*tile.x/Chunk.CHUNKSIZE;
			int yPixel = WIDTH*tile.y/Chunk.CHUNKSIZE;

//			display.drawSpriteAndSubtractYpos(map, x-xPixel+WIDTH/2, y-yPixel+WIDTH/2);
			display.drawSpriteAndSubtractYpos(map, x, y);
			
//			Sprite minimapBlack = new Sprite(ResourceLoader.getInstance().getGUI("minimap/minimap-black"));
//			display.drawSpriteAndSubtractYpos(minimapBlack, x, y);
			
			Sprite minimapRand = new Sprite(ResourceLoader.getInstance().getGUI("minimap/minimap"));
			display.drawSpriteAndSubtractYpos(minimapRand, x, y);
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
