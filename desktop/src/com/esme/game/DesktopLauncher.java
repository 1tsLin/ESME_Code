package com.esme.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.esme.game.LaunchGame;
import com.esme.game.utils.Constants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		//config.setForegroundFPS(60);
		config.setTitle("ESME");
		new Lwjgl3Application(new LaunchGame(), config);
	}
}
