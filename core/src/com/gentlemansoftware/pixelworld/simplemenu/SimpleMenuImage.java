package com.gentlemansoftware.pixelworld.simplemenu;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.helper.SpriteHelper;
import com.gentlemansoftware.pixelworld.inputs.GamePad;

public class SimpleMenuImage extends SimpleMenuNameItem {

	Sprite image;

	public SimpleMenuImage(String title, SimpleMenuNameTypes type) {
		super(title, type);
	}
	
	public void setImage(Pixmap image) {
		setImage(scaleImageToFixedSize(image));
	}

	public void setImage(Sprite image) {
		this.image = SpriteHelper.setToWidth(image, XSIZE);
	}
	
	private static final int XSIZE = 128;

	private Sprite scaleImageToFixedSize(Pixmap pixmap) {
		Sprite s = new Sprite(new Texture(pixmap));
		return s;
	}

	private int drawImage(CameraControllerInterface display, int yposStart) {
		Sprite top = new Sprite(ResourceLoader.getInstance().getGUI("sign_post/top"));
		Sprite middle = new Sprite(ResourceLoader.getInstance().getGUI("sign_post/middle"));
		Sprite bottom = new Sprite(ResourceLoader.getInstance().getGUI("sign_post/bottom"));

		Sprite chainBottom = new Sprite(ResourceLoader.getInstance().getGUI("menus/menu_information_bottom"));

		int xpos = display.getWidth() / 2 - top.getRegionWidth() / 2;
		int ypos = yposStart;

		ypos = display.drawSpriteAndSubtractYpos(top, xpos, ypos);

		int yposStartImage = ypos;
		ypos = display.drawSpriteAndSubtractYpos(middle, xpos, ypos);
		ypos = display.drawSpriteAndSubtractYpos(middle, xpos, ypos);
		ypos = display.drawSpriteAndSubtractYpos(middle, xpos, ypos);
		ypos = display.drawSpriteAndSubtractYpos(middle, xpos, ypos);

		if (this.image != null) {
			display.drawSpriteAndSubtractYpos(this.image, display.getWidth()/2-XSIZE/2, yposStartImage);
		}

		ypos = display.drawSpriteAndSubtractYpos(bottom, xpos, ypos);

		ypos = display.drawSpriteAndSubtractYpos(chainBottom, xpos, ypos);

		return ypos;
	}

	@Override
	public boolean update(GamePad gamepad) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int render(CameraControllerInterface display, int ypos) {
		ypos = super.render(display, ypos);
		ypos = drawImage(display, ypos);
		return ypos;
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		
	}

}
