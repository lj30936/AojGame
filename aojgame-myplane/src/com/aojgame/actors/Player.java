package com.aojgame.actors;

import com.aojgame.myplane.Art;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Actor{
	
	private static final float PLAYER_WIDTH 	= 100;
	private static final float PLAYER_HEIGHT 	= 120;
	private Animation 	animation;
	private Bullet		bullet;
	
	private boolean isDead;
	private float	stateTime;
	private int 	bombs;
	private int 	score;
	
	public Player(){
		animation 	= Art.animation_player;
		bombs		= 0;
		stateTime 	= 0;
		score		= 0;
		
		setName("Player");
		setHeight(PLAYER_HEIGHT);
		setWidth(PLAYER_WIDTH);
		setPosition((Gdx.graphics.getWidth()-getWidth()) / 2, 0);
		
		bullet		= new Bullet(this);
	}
	public void draw (SpriteBatch batch, float parentAlpha) {
		stateTime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(stateTime, true), getX(), getY() );
		bullet.draw(batch, parentAlpha);
		
		if ( bombs >= 0 ){
			batch.draw(Art.bomb, 0,0 );
			Art.font.draw(batch, "X" + bombs, 70, 40);
		}
		Art.font.draw(batch, Gdx.graphics.getFramesPerSecond()+"", 100, 300);
		Art.font.draw(batch, score>0?score+"000":"0", Art.gamePause.getRegionWidth() +10, Gdx.graphics.getHeight() -10 );
	}
	
	public void act (float delta) {
		super.act(delta);
		bullet.act(delta);
		
		checkCrash();
		if (isDead){
			animation = Art.animation_player_down;
		}
	}
	
	public int getScore() {
		return this.score;
	}
	
	private void checkCrash(){
		Array<Actor> actors = getStage().getActors();
		for (Actor actor : actors) {
			if (actor.getName() == null)
				continue;
			if ( actor.getName().equals("Enemy")){
				Enemy enemy = (Enemy)actor;
				if (enemy.crash(getX() + (getWidth()-50) / 2, getY(), 50, 80)){
					isDead = true;
					break;
				}
				if ( enemy.getHP() > 0 && bullet.Hit(enemy)){
					if (enemy.getType() == Enemy.TYPE_SMALL)score += 1;
					else if (enemy.getType() == Enemy.TYPE_MIDDLE)score += 6;
					else score += 30;
				}
			}
			else if (actor.getName().equals("Bomb")){
				bombs++;
				if (bombs > 3)bombs = 3;
			}
			else if (actor.getName().equals("Bullet")){
				bullet.upgrade();
			}
		}
	}
}
