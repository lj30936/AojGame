package com.aojgame.myplane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * 资源类，异步加载，加载完成后直接调用
 * 素材采用apk包中提取出来的pack文件和png文件，原装无修改
 * @author aojgame.com
 *
 */
public class Art {
	
	public static TextureAtlas 	textureAtlas_background;
	public static TextureAtlas 	textureAtlas_planes;
	
	//背景
	public static TextureRegion 	backgroud;
	public static TextureRegion 	copyright;
	//玩家飞机
	public static TextureRegion[]	player;
	public static Animation		animation_player;
	//玩家飞机爆炸
	public static TextureRegion[]	player_down;
	public static Animation		animation_player_down;
	//敌方飞机
	public static TextureRegion[] enemy1;
	public static TextureRegion[] enemy2;
	public static TextureRegion[] enemy3;
	public static Animation[]		animation_enemy;
	//击中动画
	public static TextureRegion 	enemy2_hit;
	public static TextureRegion	enemy3_hit;
	public static Animation[]		animation_enemy_hit;
	//爆炸动画
	public static TextureRegion[] enemy1_down;
	public static TextureRegion[] enemy2_down;
	public static TextureRegion[] enemy3_down;
	public static Animation[]		animation_enemy_down;

	//UFO
	public static TextureRegion		UFO_BULLET;
	public static TextureRegion		UFO_BOMB;
	//子弹
	public static TextureRegion		bullet_red;
	public static TextureRegion		bullet_bule;
	//炸弹
	public static TextureRegion		bomb;
	//按钮
	public static TextureRegion 		btn_goback;
	public static TextureRegion		gamePause;
	public static TextureRegion		gamePausePressed;
	//Loading动画
	public static TextureRegion[]		gameLoading;
	public static Animation 			animation_gameLoading;
	//字体
	public static BitmapFont			font;
	//资源加载器
	private static AssetManager		assetManager;
	//是否加载完成
	public static boolean			isLoaded;
	
	/**
	 * 这部分资源需要立即载入，其他资源可以异步载入
	 */
	public static void init(){
		
		isLoaded 	= false;
		
		assetManager = new AssetManager();
		
		//读取游戏绘图素材
		textureAtlas_background = new TextureAtlas(Gdx.files.internal("data/shoot_background.pack"));
		
		//Loading动画
		gameLoading		= new TextureRegion[4];
		gameLoading[0]	= new TextureRegion(textureAtlas_background.createSprite("game_loading1"));
		gameLoading[1]	= new TextureRegion(textureAtlas_background.createSprite("game_loading2"));
		gameLoading[2]	= new TextureRegion(textureAtlas_background.createSprite("game_loading3"));
		gameLoading[3]	= new TextureRegion(textureAtlas_background.createSprite("game_loading4"));
		
		animation_gameLoading	= new Animation(0.5f, gameLoading);
	
	}
	
	/**
	 * 载入资源
	 */
	public static void load(){
		
		assetManager.load("data/shoot.pack", TextureAtlas.class);
		//声音文件在data包里有，暂没写到程序去，请自行完善
//		assetManager.load("data/sound/game_music.mp3",Music.class);
		assetManager.load("data/ui/font.fnt", BitmapFont.class);

	}
	
	/**
	 * 异步加载资源
	 */
	public static void update(){
		
		if (assetManager.update()){
			
			textureAtlas_planes  = assetManager.get("data/shoot.pack", TextureAtlas.class);
			
			//背景，用来滚动显示
			backgroud		= textureAtlas_background.createSprite("background");
			//版权背景
			copyright		= textureAtlas_background.createSprite("shoot_copyright");
			//字体
			font 			= assetManager.get("data/ui/font.fnt", BitmapFont.class);
			font.setColor(Color.BLACK);
			
			//返回按钮
			btn_goback		= new TextureRegion(textureAtlas_background.createSprite("btn_finish"));
			
			//玩家飞机
			player			= new TextureRegion[2];
			player[0]		= textureAtlas_planes.createSprite("hero1");
			player[1]		= textureAtlas_planes.createSprite("hero2");
			animation_player= new Animation(0.3f, player);
			player_down		= new TextureRegion[4];
			player_down[0]	= textureAtlas_planes.createSprite("hero_blowup_n1");
			player_down[1]	= textureAtlas_planes.createSprite("hero_blowup_n2");
			player_down[2]	= textureAtlas_planes.createSprite("hero_blowup_n3");
			player_down[3]	= textureAtlas_planes.createSprite("hero_blowup_n4");
			animation_player_down = new Animation(0.3f, player_down);
			
			//敌方飞机
			enemy1			= new TextureRegion[1];
			enemy2			= new TextureRegion[1];
			enemy3			= new TextureRegion[2];
			enemy1[0]		= textureAtlas_planes.createSprite("enemy1");
			enemy2[0]		= textureAtlas_planes.createSprite("enemy2");
			enemy3[0]		= textureAtlas_planes.createSprite("enemy3_n1");
			enemy3[1]		= textureAtlas_planes.createSprite("enemy3_n2");
			animation_enemy = new Animation[3];
			animation_enemy[0] = new Animation(0.3f, enemy1);
			animation_enemy[1] = new Animation(0.3f, enemy2);
			animation_enemy[2] = new Animation(0.3f, enemy3);
			
			//敌方飞机被击中动画
			enemy2_hit		= textureAtlas_planes.createSprite("enemy2_hit");
			enemy3_hit		= textureAtlas_planes.createSprite("enemy3_hit");
			animation_enemy_hit 	= new Animation[3];
			animation_enemy_hit[0] 	= new Animation(0.3f, enemy1);
			animation_enemy_hit[1] 	= new Animation(0.3f, enemy2[0], enemy2_hit);
			animation_enemy_hit[2] 	= new Animation(0.3f, enemy3[0], enemy3[1], enemy3_hit);
			
			//敌方飞机爆炸动画
			enemy1_down		= new TextureRegion[4];
			enemy2_down		= new TextureRegion[4];
			enemy3_down		= new TextureRegion[6];
			enemy1_down[0]	= textureAtlas_planes.createSprite("enemy1_down1");
			enemy1_down[1]	= textureAtlas_planes.createSprite("enemy1_down2");
			enemy1_down[2]	= textureAtlas_planes.createSprite("enemy1_down3");
			enemy1_down[3]	= textureAtlas_planes.createSprite("enemy1_down4");

			enemy2_down[0]	= textureAtlas_planes.createSprite("enemy2_down1");
			enemy2_down[1]	= textureAtlas_planes.createSprite("enemy2_down2");
			enemy2_down[2]	= textureAtlas_planes.createSprite("enemy2_down3");
			enemy2_down[3]	= textureAtlas_planes.createSprite("enemy2_down4");
			
			enemy3_down[0]	= textureAtlas_planes.createSprite("enemy3_down1");
			enemy3_down[1]	= textureAtlas_planes.createSprite("enemy3_down2");
			enemy3_down[2]	= textureAtlas_planes.createSprite("enemy3_down3");
			enemy3_down[3]	= textureAtlas_planes.createSprite("enemy3_down4");
			enemy3_down[4]	= textureAtlas_planes.createSprite("enemy3_down5");
			enemy3_down[5]	= textureAtlas_planes.createSprite("enemy3_down6");
			
			animation_enemy_down = new Animation[3];
			animation_enemy_down[0]	= new Animation(0.1f, enemy1_down);
			animation_enemy_down[1]	= new Animation(0.1f, enemy2_down);
			animation_enemy_down[2]	= new Animation(0.1f, enemy3_down);
			
			//暂停按钮
			gamePause		= textureAtlas_planes.createSprite("game_pause_nor");
			gamePausePressed= textureAtlas_planes.createSprite("game_pause_pressed");
			
			//炸弹
			bomb			= textureAtlas_planes.createSprite("bomb");
			
			//子弹
			bullet_red		= textureAtlas_planes.createSprite("bullet1");
			bullet_bule		= textureAtlas_planes.createSprite("bullet2");
			
			//UFO
			UFO_BULLET		= textureAtlas_planes.createSprite("ufo1");
			UFO_BOMB		= textureAtlas_planes.createSprite("ufo2");
			
			isLoaded = true;
		}
	}
}
