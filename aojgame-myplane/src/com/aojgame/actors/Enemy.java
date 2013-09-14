package com.aojgame.actors;

import com.aojgame.myplane.Art;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 * 敌方飞机类，三种类型整合在一起
 * 实例可回收重置使用
 * @author aojgame.com
 *
 */
public class Enemy extends Actor{
	//三种飞机类型
	public static final int TYPE_SMALL		= 0;
	public static final int TYPE_MIDDLE		= 1;
	public static final int TYPE_LARGE		= 2;
	//三种飞机的HP，大小
	private static final int[] FULL_HP		= new int[] {1, 5, 15};
	private static final int[] SIZE_WIDTH	= new int[] {50, 70, 160};
	private static final int[] SIZE_HEIGHT	= new int[] {50, 90, 250};
	//基准速度，在此基础上随机增减生成速度
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
		//爆炸动画
		if ( HP <= 0 && animation.isAnimationFinished(stateTime))
			setVisible(false);
		
		if (HP <= 0)return;

		setY(getY() - speed * delta );
		
		if (getY() < -getHeight()){
			setVisible(false);
		}
		
	}
	public void draw (SpriteBatch batch, float parentAlpha) {
		//爆炸动画不需要循环
		if (HP <= 0)
			batch.draw(animation.getKeyFrame(stateTime, false), getX(), getY() );
		
		else batch.draw(animation.getKeyFrame(stateTime, true ), getX(), getY() );
	}
	
	public int getType(){
		return type;
	}
	/**
	 * 被击中
	 * @param cnt 减少的HP数
	 */
	public void beShooted(int cnt){
		//飞机刚冒出来的时候无敌
		if (getY() > Gdx.graphics.getHeight() - getHeight() / 2)
			return;
		HP -= cnt;
		if ((type == TYPE_MIDDLE && HP <= 2)
			||(type == TYPE_LARGE && HP <= 10 ))
			animation = Art.animation_enemy_hit[type];

		if (HP <= 0) {
			//HP小于0时切换到爆炸动画
			animation 	= Art.animation_enemy_down[type];
			stateTime	= 0;
		}
	}
	/**
	 * 回收实例并重置
	 * @param level 当前级别，级别可根据玩家分数调整
	 */
	public void reSet(float level){
		type 		= TYPE_SMALL;
		//生成中等飞机的概率
		if (MathUtils.random() * level >= 1f-1/10f)
			type	= TYPE_MIDDLE;
		//生成大飞机的概率
		if (MathUtils.random() * level >= 1f-1/30f)
			type 	= TYPE_LARGE;
		
		animation	= Art.animation_enemy[type];
		//基准速度+-500 作为速度范围
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
