package com.gentlemansoftware.pixelworld.helper;

import com.badlogic.gdx.Input.TextInputListener;
import com.gentlemansoftware.easyServer.EasyServerCommunicationSend;

public class MyTextInputListener implements TextInputListener {
	EasyServerCommunicationSend connection;
	
	public MyTextInputListener(EasyServerCommunicationSend connection) {
		// TODO Auto-generated constructor stub
		this.connection = connection;
	}

	@Override
	public void input(String text) {
		connection.sendMessage(text);
	}

	@Override
	public void canceled() {
	}
}