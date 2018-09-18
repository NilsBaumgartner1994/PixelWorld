package com.gentlemansoftware.pixelworld.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gentlemansoftware.pixelworld.game.Main;

public class DesktopLauncher {

	public static String title = "God of Forest";

	static Resolutions resolution = Resolutions.XGA;

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.vSyncEnabled = true;
		config.backgroundFPS = 0;
		config.foregroundFPS = 0;

		switch (resolution) {
		case IPHONE:
			config.width = 568;
			config.height = 300;
			break;
		case IPHONE7:
			config.width = 1334;
			config.height = 750;
			break;
		case XGA:
			config.width = 1024;
			config.height = 768;
			break;
		case HD:
			config.width = 1360;
			config.height = 768;
			break;
		case FULLHD:
			config.width = 1920;
			config.height = 1080;
			config.fullscreen = true;
			break;
		}
		config.title = title;
		new LwjglApplication(new Main(), config);
		// new LwjglApplication(new MinecraftClone(), config);
		// new LwjglApplication(new LoadModelsTest(), config);
	}
}
