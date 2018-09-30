package com.gentlemansoftware.pixelworld.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.gentlemansoftware.easyServer.EasyRunnableParametersInterface;

public class MyTextInputListener implements TextInputListener {
	public EasyRunnableParametersInterface<?> runnable;
	public String dialogTitle;
	public String initialValue;
	public String hintValue;

	public MyTextInputListener(EasyRunnableParametersInterface<?> runnable, String dialogTitle, String initialValue,
			String hintValue) {
		this.runnable = runnable;
		this.dialogTitle = dialogTitle;
		this.initialValue = initialValue;
		this.hintValue = hintValue;
	}

	@Override
	public void input(String text) {
		if (runnable != null) {
			runnable.setParam(text);
			runnable.run();
		}
	}

	public void getInput() {
		Gdx.input.getTextInput(this, dialogTitle, initialValue, hintValue);
	}

	@Override
	public void canceled() {
	}
}