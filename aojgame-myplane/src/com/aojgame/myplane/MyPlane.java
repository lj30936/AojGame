package com.aojgame.myplane;

import com.aojgame.screen.LoadingScreen;
import com.badlogic.gdx.Game;
/**
 * Game类，只简单做了Loading界面和游戏界面
 * @author aojgame.com
 *
 */
public class MyPlane extends Game {

	public void create() {
		this.setScreen(new LoadingScreen(this));
	}

}
