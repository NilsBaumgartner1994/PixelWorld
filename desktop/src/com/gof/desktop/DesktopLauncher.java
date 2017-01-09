package com.gof.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gof.game.Main;

public class DesktopLauncher {
	public static String title = "God of Forest";

	static final String iphone = "iphone";
	static final String svga = "svga";
	static final String xga = "xga";
	static final String hd = "hd";
	static final String fullHD = "fullHD";

	static String resolution = xga;

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.vSyncEnabled = false;
		
		switch (resolution) {
		case iphone:
			config.width = 568;
			config.height = 300;
			break;
		case svga:
			config.width = 800;
			config.height = 600;
			break;
		case xga:
			config.width = 1024;
			config.height = 768;
			break;
		case hd:
			config.width = 1360;
			config.height = 768;
			break;
		case fullHD:
			config.width = 1920;
			config.height = 1080;
			config.fullscreen = true;
			break;
		}
		config.title = title;
		new LwjglApplication(new Main(), config);
	}
}
