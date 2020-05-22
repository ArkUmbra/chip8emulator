package com.arkumbra.startchip8.gdx.desktop;

import com.arkumbra.startchip8.gdx.SaveStateFileManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.arkumbra.startchip8.gdx.GdxOutputter;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		SaveStateFileManager saveStateFileManager = new SaveStateFileManagerImpl();
		GdxOutputter gdxOutputter = new GdxOutputter(saveStateFileManager);

		new LwjglApplication(gdxOutputter, config);
	}
}
