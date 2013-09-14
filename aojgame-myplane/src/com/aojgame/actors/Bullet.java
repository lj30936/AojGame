package com.aojgame.actors;

import com.aojgame.myplane.Art;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 * 子弹类，集成在{@link Player}中，不加入stage
 * @author aojgame.com
 *
 */
public class Bullet extends Actor{

	//双倍子弹的使用时间
	private static final float	BLUE_TIME		= 15;
	private static final float	BULLET_WIDTH 	= 10;
	private static final float 	BULLET_HEIGHT 	= 10;
	private static final float	BULLET_SPEED	= 1500;
	//双倍子弹时两子弹的X轴距离，碰撞判断时用到
	private static final float	DELTA_X			= 30;
	
	private boolean isDouble;
	private float	blueTime;
	private Player			player;
	
	public Bullet(Player player) {
		isDouble 	= false;
		this.player = player;
		
		setX(player.getX() + player.getWidth() / 2);
		setY(player.getY() + player.getHeight()) ;
		
		setWidth(BULLET_WIDTH);
		setHeight(BULLET_HEIGHT);

	}
	public void act (float delta) {
		setY(getY() + BULLET_SPEED * delta );
		
		//子弹击中目标时，重新发射
		if (isDouble){
			blueTime += Gdx.graphics.getDeltaTime();
			if (blueTime > BLUE_TIME){
				isDouble = false;
				reShoot();
			}
		}
		//子弹超出屏幕时，重新发射
		if (getY() >= Gdx.graphics.getHeight())
			reShoot();
	}
	public void draw (SpriteBatch batch, float parentAlpha) {
		if (isDouble){
			batch.draw(Art.bullet_bule, getX(), getY());
			batch.draw(Art.bullet_bule, getX() + 2*DELTA_X, getY());
		}
		else{
			batch.draw(Art.bullet_red, getX() - getWidth() / 2, getY());
		}
	}
	/**
	 * 击中敌机判断，双倍子弹时需要判断两个子弹
	 * @param enemy
	 * @return
	 */
	public boolean Hit(Enemy enemy){
		if (isDouble && 
				(crash(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())
					|| crash(enemy.getX() - 2*DELTA_X,enemy.getY(), enemy.getWidth(), enemy.getHeight()))){
				reShoot();
				enemy.beShooted(1);
				return true;
		}
		if ( !isDouble && crash(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())){
			reShoot();
			enemy.beShooted(1);
			return true;
		}
		return false;
	}
	/**
	 * 升级为双倍子弹
	 */
	public void upgrade(){
		blueTime = 0;
		isDouble = true;
		reShoot();
	}
	/**
	 * 重新发射
	 */
	public void reShoot(){
		if (isDouble){
			setX(player.getX() + player.getWidth() / 2 - DELTA_X);
			setY(player.getY() + player.getHeight() ) ;
		}
		else {
			setX(player.getX() + player.getWidth() / 2 );
			setY(player.getY() + player.getHeight() ) ;
		}
	}
	private boolean crash (float x, float y, float width, float height) {
		return !(getX() > x + width || getX() + getWidth() < x || 
				getY() > y + height || getY() + getHeight() < y);
	}
}
