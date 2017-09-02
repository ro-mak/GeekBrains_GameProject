package ru.makproductions.geekbrains.gameproject.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.Ship;
import ru.makproductions.geekbrains.gameproject.engine.Base2DScreen;
import ru.makproductions.geekbrains.gameproject.engine.buttons.Button;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.screens.game.stars.Star;


public class MenuScreen extends Base2DScreen {

    private static final float STAR_WIDTH = 0.03f;

    private TextureAtlas atlas;

    private Background background;

    private Star[] stars;

    private Ship ship;

    private Button startButton;
    private Music music_level1;

    public MenuScreen(Game game){
        super(game);
    }

    @Override
    public void show () {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        background = new Background(atlas.findRegion("StartOfStarFighter"));

        stars = new Star[25];
        for(int i = 0; i < stars.length; i++) {
            float starWidth = STAR_WIDTH * Rnd.nextFloat(0.5f,0.8f);
            float vx = Rnd.nextFloat(0,0.006f);
            float vy = Rnd.nextFloat(-0.0008f,0.0008f);
            stars[i] = new Star(atlas.findRegion("Star")
                    , vx, vy, starWidth);
        }
        startButton = new Button(atlas.findRegion("StartButton"),0.4f);
        ship = new Ship(atlas.findRegion("Ship"),0,0,0.2f,new Vector2(0f,-0.2f));
        playMusic();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        ship.resize(worldBounds);
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        System.out.println(touch);
    }

    @Override
    public void render (float delta) {
        update(delta);
        draw();
    }

    private void playMusic(){
        music_level1 = Gdx.audio.newMusic(Gdx.files.internal("music/StarFighterMusicWarriorDrums.wav"));
        music_level1.setLooping(true);
        music_level1.play();
    }

    private void update(float deltaTime){
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(deltaTime);
        }
    }

    private void draw(){
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        startButton.draw(batch);
        ship.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        //System.out.println("Key "+ keycode + " Down");
//        if(keycode == Input.Keys.A){
//            ship.setX(ship.getX() - ship.getSpeed());
//        }
//        if(keycode == Input.Keys.D){
//            ship.setX(ship.getX() + ship.getSpeed());
//        }
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
//        if(screenX <600 && screenX > 50 && screenY > 260 && screenY < 330){
//            ship.setEngineStarted(true);
//        }
        Vector2 touchCoordinates = new Vector2(screenX,revertY(screenY));
        if(startButton.isMe(touchCoordinates.mul(matScreenToWorld)))startButton.touchDown(touchCoordinates,pointer);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
     //   System.out.println("Screen touchedUp: x:" + screenX + " y:"+screenY);
        Vector2 touchCoordinates = new Vector2(screenX,revertY(screenY));
        if(startButton.isMe(touchCoordinates.mul(matScreenToWorld)))startButton.touchUp(touchCoordinates,pointer);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
      //  System.out.println("Screen touchedDragged: x:" + screenX + " y:"+screenY);
        Vector2 touchCoordinates = new Vector2(screenX,revertY(screenY));
        if(!startButton.isMe(touchCoordinates.mul(matScreenToWorld)))startButton.touchMove(touchCoordinates,pointer);
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
