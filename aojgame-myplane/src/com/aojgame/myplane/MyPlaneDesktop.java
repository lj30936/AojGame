package com.aojgame.myplane;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MyPlaneDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "aojgame-myplane";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new MyPlane(), cfg);
	}
}
