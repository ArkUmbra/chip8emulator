package com.arkumbra.startchip8.gdx.desktop;

import com.arkumbra.startchip8.gdx.SaveStateFileManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.arkumbra.startchip8.gdx.GdxGameWindow;

public class DesktopLauncher {
	public static void main(String[] arg) {

		if (arg.length == 0) {
			System.err.println("Must provide a path to a game file");
			System.exit(1);
		}

		String gameFileRelativePath = arg[0];



		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 320;
//		config.fullscreen = false;
//		config.resizable = false;

		SaveStateFileManager saveStateFileManager = new SaveStateFileManagerImpl();
		GdxGameWindow gdxGameWindow = new GdxGameWindow(saveStateFileManager);

		LwjglApplication gdxApp = new LwjglApplication(gdxGameWindow, config);
		gdxGameWindow.setUpChip8();
		gdxGameWindow.loadGame(gameFileRelativePath);
		gdxGameWindow.startChip8();

		DebugPanel debugPanel = new DebugPanel(gdxGameWindow.getDebugger(), gdxGameWindow.getSaveStateHandler(), saveStateFileManager);
	}
}
