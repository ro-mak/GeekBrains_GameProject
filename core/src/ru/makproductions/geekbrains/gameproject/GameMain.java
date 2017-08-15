package ru.makproductions.geekbrains.gameproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture img2;
	float x;
	float y;
	float i = 1;
	float j = 1;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("GeekBackGround.png");
		img2 = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, -70);
		bounceBadlogicImage();
		batch.end();
	}

	private void bounceBadlogicImage(){

		if(x < 0){
			x = 0;
			i = 1;
		}
		if(x > 200){
			x = 200;
			i = -1;
		}
		if(y > 200){
			x = 200;
			j = -1;
		}
		if(y < 0){
			y = 0;
			j = 1;
		}
		batch.draw(img2,x,y);
		x+=i;
		y+=j;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
