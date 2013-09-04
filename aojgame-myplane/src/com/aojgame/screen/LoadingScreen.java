package com.aojgame.screen;

import com.aojgame.myplane.Art;
import com.aojgame.myplane.MyPlane;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LoadingScreen implements Screen{
	
	private int			width;
	private int			height;
	private float			statetime;
	private boolean		init;
	
	private MyPlane			game;
	private Stage			stage;
	private Button			btn_goback;
	private Actor			actor_loading;
	private Actor			actor_title;
	
	public LoadingScreen(MyPlane game) {
		this.game = game;
	}
	@Override
	public void render(float delta) {

		Art.update();
		init();
		
	    statetime += Gdx.graphics.getDeltaTime();
	    if (statetime > 1){
	    	game.setScreen(new GameScreen());
	    	return;
	    }
	    	
	    if (!init){
	    	Gdx.gl.glClearColor(0, 0, 0, 0);
		    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    }
	    else{
	    	Gdx.gl.glClearColor(1, 1, 1, 1);
	    	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    }
	    stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
		init	= false;
		
		Art.init();
		Art.load();
		
		statetime = 0;
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		new SpriteBatch();
		stage		= new Stage();
		
		actor_loading = new Actor(){
			public void draw (SpriteBatch batch, float parentAlpha) {
				if (!Art.isLoaded)
				batch.draw(Art.animation_gameLoading.getKeyFrame(statetime, true), 
		    			width / 3, height / 2);
			}
		};
		stage.addActor(actor_loading);
	}

	/**
	 * 等待资源加载完毕后进行把成员实例化
	 */
	private void init(){
		if (init)return;
		
		if (Art.isLoaded){
			actor_title	= new Actor(){
				public void draw (SpriteBatch batch, float parentAlpha) {
					if (Art.isLoaded){
						batch.draw(Art.backgroud, 0, 0);
						batch.draw(Art.copyright, width / 2 - Art.copyright.getRegionWidth() / 2,
			    			height / 2 );
					}
				}
			};
			stage.addActor(actor_title);
			actor_loading.remove();
			
			btn_goback	= new Button(new TextureRegionDrawable(Art.btn_goback) );
			btn_goback.setPosition(width / 2 ,Art.btn_goback.getRegionHeight());
			btn_goback.addListener(new InputListener(){
				public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
					Gdx.app.exit();
					super.touchUp(event, x, y, pointer, button);
				}
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					return true;
				}
			});
			
			stage.addActor(btn_goback);
			Gdx.input.setInputProcessor(stage);
			init = true;
		}
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
}
