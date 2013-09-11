package com.aojgame.actors;

import com.aojgame.myplane.Art;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bonus extends Actor{

	public static final	int TYPE_BULLET 		= 0;
	public static final	int TYPE_BOMB			= 1;
	
	private static final	int 	BONUS_WIDTH 	= 60;
	private static final	int 	BONUS_HEIGHT 	= 100;
	private static final	float 	SPEED			= 200;
	private int type;
	
	public Bonus(){
		setWidth(BONUS_WIDTH);
		setHeight(BONUS_HEIGHT);
		reSet();
	}
	
	public void act (float delta) {
		setY(getY() - SPEED * delta);
	}
	
	public void draw (SpriteBatch batch, float parentAlpha) {
		if (type == TYPE_BOMB)
			batch.draw(Art.UFO_BOMB, getX(), getY());
		else 
			batch.draw(Art.UFO_BULLET,  getX(), getY());
	}
	
	public void reSet(){
		type = MathUtils.random(0,1);
		if (type == TYPE_BOMB)
			setName("Bomb");
		else setName("Bullet");
		
		setVisible(true);
		setX(MathUtils.random() * (Gdx.graphics.getWidth() - BONUS_WIDTH ));
		setY(Gdx.graphics.getHeight());
	}
}
