package com.aojgame.screen;

import java.util.ArrayList;

import com.aojgame.actors.Bonus;
import com.aojgame.actors.Enemy;
import com.aojgame.actors.Player;
import com.aojgame.myplane.Art;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
/**
 * 
 * @author aojgame
 *
 */
public class GameScreen implements Screen, InputProcessor {

	private static final int 	BACKGROUND_MOVE 	= 1;
	private static final int 	MAX_ENEMY		= 20;
	private static final float 	GEN_ENEMY_TIME	= 1f;
	private static final float	GEN_BONUS_TIME	= 20f;
	
	private Stage				stage;
	private Player 				player;
	private Bonus				bonus;
	private Image				background;
	private ImageButton			btn_pause;
	private ArrayList<Enemy> 	enemies;
	
	private	float		level;
	private int 		background_Y;
	private int		preX ;
	private int		preY ;
	private float		countTime = 0;
	private float		bonusTime = 0;
	private float		lastInputTime	= 0;
	private	float		nowInputTime	= 0;
	
	@Override
	public void render(float delta) {

	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    if (player.isOver())
	    	Gdx.app.exit();
	    
		background_Y -= BACKGROUND_MOVE;
		if (background_Y <= -Art.backgroud.getRegionHeight())
		    background_Y = 0;
		
		countTime += delta;
		bonusTime += delta;
		nowInputTime += delta;
		
		while (countTime  * level * MathUtils.random(0.8f, 1.2f)
				> GEN_ENEMY_TIME){
			genEnemy();
			countTime -= GEN_ENEMY_TIME;
		}
		if (bonusTime * level * MathUtils.random(0.8f, 1.2f)
				> GEN_BONUS_TIME){
			bonus.reSet();
			bonusTime -= GEN_BONUS_TIME;
		}
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		level		= 1f;
		
		btn_pause	= new ImageButton(new TextureRegionDrawable(Art.gamePause), 
										new TextureRegionDrawable(Art.gamePausePressed));
		btn_pause.setPosition(10,Gdx.graphics.getHeight() - 50);
		btn_pause.addListener(new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

				super.touchUp(event, x, y, pointer, button);
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		
		enemies		= new ArrayList<Enemy>();
		
		background = new Image(Art.backgroud){
			public void draw (SpriteBatch batch, float parentAlpha) {
				setPosition(0, background_Y);
				super.draw(batch, parentAlpha);
				setPosition(0, background_Y + getHeight());
				super.draw(batch, parentAlpha);
			}
		};
		
		bonus 	= new Bonus();
		bonus.setVisible(false);
		
		stage 	= new Stage();
		player 	= new Player();
		
		
		stage.addActor(background);
		stage.addActor(player);
		stage.addActor(bonus);
		stage.addActor(btn_pause);
		
		Gdx.input.setInputProcessor(this);
		Gdx.gl.glClearColor(1, 1, 1, 1);
	}

	public void genEnemy() {
		int len = enemies.size();
		for (int i = 0; i < len ; i++){
			Enemy enemy = enemies.get(i);
			if (!enemy.isVisible()){
				enemy.reSet(level);
				return;
			}
		}
		if (len == MAX_ENEMY)
			return;
		
		Enemy enemy = new Enemy(level);
		enemies.add(enemy);
		stage.addActor(enemy);
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
		
		if(nowInputTime - lastInputTime < 0.8f){
			//player.useBomb();
			nowInputTime = 0;
		}
		lastInputTime = nowInputTime;
		//System.out.println("touch " + (preX) + "   "+ (preY) );
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
