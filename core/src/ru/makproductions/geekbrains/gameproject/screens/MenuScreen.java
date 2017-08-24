package ru.makproductions.geekbrains.gameproject.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.makproductions.geekbrains.gameproject.Ship;
import ru.makproductions.geekbrains.gameproject.engine.Base2DScreen;


public class MenuScreen extends Base2DScreen {
    SpriteBatch batch;
    Texture img;
    Ship ship;

    public MenuScreen(Game game){
        super(game);
    }



    @Override
    public void show () {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("StartOfStarFighter.png");
        ship = new Ship();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        ship.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        ship.getTexture().dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        //System.out.println("Key "+ keycode + " Down");
        if(keycode == Input.Keys.A){
            ship.setX(ship.getX() - ship.getSpeed());
        }
        if(keycode == Input.Keys.D){
            ship.setX(ship.getX() + ship.getSpeed());
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        //System.out.println("Key " + keycode+ " Up");
        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        //System.out.println("Key " + character + " typed");
        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
       // System.out.println("Screen touchedDown: x:" + screenX + " y:"+screenY);
        if(screenX <600 && screenX > 50 && screenY > 260 && screenY < 330){
            ship.setEngineStarted(true);
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
     //   System.out.println("Screen touchedUp: x:" + screenX + " y:"+screenY);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
      //  System.out.println("Screen touchedDragged: x:" + screenX + " y:"+screenY);
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
      //  System.out.println("MouseMoved: x:" + screenX + " y:"+screenY);
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
      //  System.out.println("Scrolled:" +amount);
        return super.scrolled(amount);
    }
}