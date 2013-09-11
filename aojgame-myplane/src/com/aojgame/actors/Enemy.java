package com.aojgame.actors;

import com.aojgame.myplane.Art;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor{
	
	public static final int TYPE_SMALL		= 0;
	public static final int TYPE_MIDDLE		= 1;
	public static final int TYPE_LARGE		= 2;
	
	private static final int[] FULL_HP		= new int[] {1, 5, 15};
	private static final int[] SIZE_WIDTH	= new int[] {50, 70, 160};
	private static final int[] SIZE_HEIGHT	= new int[] {50, 90, 250};
	
	private static final float	NORMAL_SPEED = 100;
	
	private int 	type;
	private int 	HP;
	private	 float	speed;
	private float	stateTime = 0;
	
	private Animation 		animation;
	
	public Enemy( float level ) {
		setName("Enemy");
		reSet(level);
	}
	public void act (float delta) {
		
		stateTime += delta;
		
		if ( HP == 0 && animation.isAnimationFinished(stateTime))
			setVisible(false);
		
		if (HP == 0)return;

		setY(getY() - speed * delta );
		
		if (getY() < -getHeight()){
			setVisible(false);
		}
		
	}
	public void draw (SpriteBatch batch, float parentAlpha) {
		if (HP == 0)
			batch.draw(animation.getKeyFrame(stateTime, false), getX(), getY() );
		
		else batch.draw(animation.getKeyFrame(stateTime, true ), getX(), getY() );
	}
	
	public int getType(){
		return type;
	}
	
	public void beShooted(int cnt){
		HP -= cnt;
		if (HP <= 0) {
			animation 	= Art.animation_enemy_down[type];
			stateTime	= 0;
		}
	}
	public void reSet(float level){
		type 		= TYPE_SMALL;
		
		if (MathUtils.random() * level >= 1f-1/10f)
			type	= TYPE_MIDDLE;
		if (MathUtils.random() * level >= 1f-1/30f)
			type 	= TYPE_LARGE;
		
		animation	= Art.animation_enemy[type];
		speed 		= NORMAL_SPEED + MathUtils.random(500f) * level;
		HP			= FULL_HP[type];
		
		setWidth(SIZE_WIDTH[type]);
		setHeight(SIZE_HEIGHT[type]);
		
		setX(MathUtils.random(Gdx.graphics.getWidth() - getWidth()));
		setY(Gdx.graphics.getHeight());
		
		setVisible(true);
	}
	public int getHP(){
		return HP;
	}
}
