package com.arkumbra.startchip8.gdx.desktop;

import com.arkumbra.startchip8.gdx.SaveStateFileManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.arkumbra.startchip8.gdx.GdxGameWindow;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 320;
		config.fullscreen = false;
		config.resizable = false;

		SaveStateFileManager saveStateFileManager = new SaveStateFileManagerImpl();
		GdxGameWindow gdxGameWindow = new GdxGameWindow(saveStateFileManager);
		new LwjglApplication(gdxGameWindow, config);
	}
}
