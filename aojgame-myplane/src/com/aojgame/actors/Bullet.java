package com.aojgame.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class Bullet extends Actor{

	private static final float	BULLET_WIDTH 	= 5;
	private static final float 	BULLET_HEIGHT 	= 10;
	private static final float 	speed			= 10;
	private boolean isDouble;
	
	private MoveToAction action;
	
	public Bullet(float x, float y) {
		isDouble = false;
		
		setPosition(x, y);
		
		action		= new MoveToAction();
		action.setDuration( Gdx.graphics.getHeight() / speed );
		action.setPosition(getX(), 0-getHeight());
		
		addAction(action);
	}
	public void draw (SpriteBatch batch, float parentAlpha) {
		if (isDouble){
			batch.draw(blue, getX(), getY(), getWidth(), getHeight());
			batch.draw(blue, getX(), getY(), getWidth(), getHeight());
		}
		else{
			batch.draw(red, getX(), getY(), getWidth(), getHeight());
		}
	}
	
	public void upgrade(){
		isDouble = true;
		setPosition(x, y);
		
		action.setPosition(getX(), 0-getHeight());
		action.restart();
	}
}
