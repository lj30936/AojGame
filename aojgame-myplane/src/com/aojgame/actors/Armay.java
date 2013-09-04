package com.aojgame.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class Armay extends Actor{
	
	private static final int TYPE_SMALL		= 0;
	private static final int TYPE_MIDDLE	= 1;
	private static final int TYPE_UFO		= 2;
	
	private static final int[] FULL_HP		= new int[] {1, 5, 15};
	private static final int[] SIZE_WIDTH	= new int[] {5, 10, 50};
	private static final int[] SIZE_HEIGHT	= new int[] {5, 12, 70};
	
	private int 	type;
	private int 	HP;
	private	float	speed;
	private float	stateTime = 0;
	
	private Animation 		animation;
	private MoveToAction	action;
	
	public Armay( float level ) {
		
		type 		= TYPE_SMALL;
		
		if (MathUtils.random() * level >= 1-1/10)
			type	= TYPE_MIDDLE;
		if (MathUtils.random() * level >= 1-1/30)
			type 	= TYPE_UFO;
		
		speed 		= MathUtils.random(5f) * level;
		HP			= FULL_HP[type];
		
		setWidth(SIZE_WIDTH[type]);
		setHeight(SIZE_HEIGHT[type]);
		setName("Armay");
		
		setX(MathUtils.random(Gdx.graphics.getWidth() - getWidth()));
		setY(Gdx.graphics.getHeight());
		
		action		= new MoveToAction();
		action.setDuration( Gdx.graphics.getHeight() / speed );
		action.setPosition(getX(), 0-getHeight());
		
		addAction(action);
	}
	
	public void draw (SpriteBatch batch, float parentAlpha) {
		
		stateTime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(stateTime), getX(), getY() );
		
		super.draw(batch, parentAlpha);
	}
	
	public boolean isLive(){
		return HP > 0 ? true : false;
	}
	
	public void beShooted(){
		HP--;
		if (HP == 0) {
			animation 	= null;
		}
	}
	
	public boolean crash (float x, float y, float width, float height) {
		return !(getX() > x + width || getX() + getWidth() < x || 
				getY() > y + height || getY() + getHeight() < y);
	}
}
