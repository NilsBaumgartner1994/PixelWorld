package com.gentlemansoftware.pixelworld.simplemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.easyGameNetworkProtocol.EasyGameCommunicationProtocol;
import com.gentlemansoftware.easyServer.EasyRunnableParameters;
import com.gentlemansoftware.easyServer.EasyRunnableParametersInterface;
import com.gentlemansoftware.pixelworld.game.CameraControllerInterface;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.helper.MyTextInputListener;
import com.gentlemansoftware.pixelworld.inputs.GamePad;
import com.gentlemansoftware.pixelworld.profiles.VarHolder;

public class SimpleMenuStringEditable implements SimpleMenuComponent {

	VarHolder<String> number;
	boolean active;
	private MyTextInputListener myListener;
	private EasyRunnableParametersInterface callbackOnChange;

	public SimpleMenuStringEditable(VarHolder<String> obj) {
		setContent(obj);
		setActive(false);
		myListener = new MyTextInputListener(createRunnableEdit(), obj.getName(), obj.getVar().toString(),
				"");
	}
	
	public void setChangeCallback(EasyRunnableParametersInterface callback){
		this.callbackOnChange = callback;
	}
	
	private EasyRunnableParametersInterface<String> createRunnableEdit() {
		EasyRunnableParametersInterface<String> aRunnable = new EasyRunnableParameters<String>() {
			public void run() {
				number.setVar(this.getParam());
				if(callbackOnChange!=null){
					callbackOnChange.run();
				}
			}
		};

		return aRunnable;
	}

	public void setContent(VarHolder<String> content) {
		this.number = content;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int render(CameraControllerInterface display, int yposStart) {
		int ypos = yposStart;
		int helper = ypos;

		ypos = drawSingleContent(display, ypos);
		drawText(display, this.number.getName(), helper);
		helper = ypos;

		ypos = drawSingleContent(display, ypos);
		drawText(display, this.number.getVar().toString(), helper);

		return ypos;
	}

	private int drawSingleContent(CameraControllerInterface display, int ypos) {
		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menus/menu_information_top"));
		int xpos = display.getWidth() / 2 - post_middle.getRegionWidth() / 2;

		ypos = display.drawSpriteAndSubtractYpos(post_middle, xpos, ypos);
		return ypos;
	}

	private void drawText(CameraControllerInterface display, String text, int ypos) {
		Sprite post_middle = new Sprite(ResourceLoader.getInstance().getGUI("menus/menu_information_top"));

		display.getFont().setColor(getColor());
		display.getLayout().setText(display.getFont(), text);
		int stringWidth = (int) display.getLayout().width;
		int stringHeight = (int) display.getLayout().height;

		int xpos = display.getWidth() / 2;

		display.getFont().draw(display.getSpriteBatch(), text, xpos - stringWidth / 2,
				ypos - post_middle.getHeight() / 2 + stringHeight / 2);
	}

	public Color getColor() {
		return this.active ? Color.GOLD : Color.FIREBRICK;
	}

	@Override
	public boolean update(GamePad gamepad) {

		return true;
	}

	@Override
	public void select() {
		myListener.getInput();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
