package me.giverplay.tatakae.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.giverplay.tatakae.Tatakae;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(Tatakae.SCREEN_WIDTH, Tatakae.SCREEN_HEIGHT);
		config.setResizable(false);
		config.useVsync(true);
		config.setTitle(Tatakae.NAME);

		new Lwjgl3Application(Tatakae.getInstance(), config);
	}
}
