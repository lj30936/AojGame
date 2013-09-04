package com.aojgame.screen;

import com.aojgame.actors.Player;
import com.aojgame.myplane.Art;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameScreen implements Screen, InputProcessor {

	private static int BACKGROUND_MOVE = 3;
	
	private Stage	stage;
	private Player player;
	private Image	background;
	
	private int 	background_Y;
	
	private int	preX ;
	private int	preY ;
	
	@Override
	public void render(float delta) {
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    background_Y -= BACKGROUND_MOVE;
	    if (background_Y <= -Art.backgroud.getRegionHeight())
	    	background_Y = 0;
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		background = new Image(Art.backgroud){
			public void draw (SpriteBatch batch, float parentAlpha) {
				setPosition(0, background_Y);
				super.draw(batch, parentAlpha);
				setPosition(0, background_Y + getHeight());
				super.draw(batch, parentAlpha);
			}
		};
		
		stage 	= new Stage();
		player 	= new Player();
		
		stage.addActor(background);
		stage.addActor(player);
		
		Gdx.input.setInputProcessor(this);
		Gdx.gl.glClearColor(1, 1, 1, 1);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		preX = screenX;
		preY = screenY;
		System.out.println("touch " + (preX) + "   "+ (preY) );
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		System.out.println("dragg " + preX + "," + preY + "   "+
				screenX  + "   "+ screenY + "  " + pointer);
		player.setX(player.getX() + screenX - preX);
		player.setY(player.getY() -( screenY - preY ));
		
		preX = screenX;
		preY = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
