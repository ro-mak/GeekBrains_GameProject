package ru.makproductions.geekbrains.gameproject.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.makproductions.geekbrains.gameproject.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		float aspect = 9f/16f;
		//float aspect = 3f/4f;
		config.width = 360;
		config.height = (int)(config.width / aspect);
		new LwjglApplication(new StarGame(), config);
	}

}
