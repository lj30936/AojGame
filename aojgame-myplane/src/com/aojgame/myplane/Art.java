package com.aojgame.myplane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art {
	
	public static TextureAtlas 	textureAtlas_background;
	public static TextureAtlas 	textureAtlas_planes;
	
	//背景
	public static TextureRegion 	backgroud1;
	public static TextureRegion 	backgroud2;
	public static TextureRegion 	copyright;
	//返回按钮
	public static TextureRegion 	btn_goback;
	//Loading动画
	public static TextureRegion[]	gameLoading;
	public static Animation 		animation_gameLoading;
	//字体
	public static BitmapFont		font;
	
	private static AssetManager	assetManager;
	
	public static boolean			isLoaded;
	//这部分资源需要立即载入，其他资源可以异步载入
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
	
	//异步载入资源
	public static void load(){
		
		assetManager.load("data/shoot.pack", TextureAtlas.class);
		assetManager.load("data/sound/game_music.mp3",Music.class);
		assetManager.load("data/ui/font.fnt", BitmapFont.class);

	}
	
	public static void update(){
		
		if (assetManager.update()){
			
			textureAtlas_planes  = assetManager.get("data/shoot.pack", TextureAtlas.class);
			
			//背景，用来滚动显示
			backgroud1		= textureAtlas_background.createSprite("background");
			backgroud2		= textureAtlas_background.createSprite("background");
			//版权背景
			copyright		= textureAtlas_background.createSprite("shoot_copyright");
			//字体
			font 			= assetManager.get("data/ui/font.fnt", BitmapFont.class);
			//返回按钮
			btn_goback		= new TextureRegion(textureAtlas_background.createSprite("btn_finish"));
			
			isLoaded = true;
		}
	}
}
