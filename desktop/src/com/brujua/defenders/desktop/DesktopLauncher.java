package com.brujua.defenders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.brujua.defenders.DefendersContest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title ="Defenders Contest";
		config.width = /*(int) DefendersContest.WORLD_WIDHT;*/ 1024;
		config.height = /*(int) DefendersContest.WORLD_HEIGHT;*/ 576;
		//config.fullscreen =true;
		new LwjglApplication(new DefendersContest(), config);
	}
}
