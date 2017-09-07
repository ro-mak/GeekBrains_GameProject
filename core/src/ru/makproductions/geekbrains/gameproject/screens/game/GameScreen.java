package ru.makproductions.geekbrains.gameproject.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.Base2DScreen;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.screens.Background;
import ru.makproductions.geekbrains.gameproject.screens.stars.StarsOfGame;


public class GameScreen extends Base2DScreen {
    public GameScreen(Game game) {
        super(game);
    }

    private TextureAtlas atlas;
    private Background background;
    private Music music_level1;
    private StarsOfGame[] stars;
    private final int STARS_COUNT = 250;
    private final float STARS_HEIGHT = 0.05f;
    private GameShip ship;
    private final float SHIP_HEIGHT = 0.2f;
    private TextureRegion[] shipTextureRegions = new TextureRegion[2];
    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        shipTextureRegions[0] = atlas.findRegion("Ship");
        shipTextureRegions[1] = atlas.findRegion("Fire");
        background = new Background(atlas.findRegion("Galaxies"));
        stars = new StarsOfGame[STARS_COUNT];
        ship = new GameShip(shipTextureRegions,0,0,SHIP_HEIGHT,new Vector2(0f,0f));
        ship.setEngineStarted(true);
        for (int i = 0; i < stars.length; i++) {
            float starHeight = STARS_HEIGHT * Rnd.nextFloat(0.5f,0.8f);
            float vx = Rnd.nextFloat(-0.0008f,0.0008f);
            float vy = Rnd.nextFloat(-0.0008f,0.0008f);
            stars[i] = new StarsOfGame(atlas.findRegion("Star"),ship,vx,vy,starHeight);
        }
        playMusic();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        ship.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
    }

    private void playMusic(){
        music_level1 = Gdx.audio.newMusic(Gdx.files.internal("music/StarFighterMusicWarriorDrums.wav"));
        music_level1.setLooping(true);
        music_level1.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(float delta){
        ship.update(delta);
        background.update(delta);
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
    }
    private void checkCollisions(){

    }

    private void deleteAllDestroyed(){

    }

    private void draw(){
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        ship.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        atlas.dispose();
        music_level1.dispose();
        super.dispose();
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        ship.touchDown(touch,pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        ship.touchUp(touch,pointer);
    }

    @Override
    protected void touchMove(Vector2 touch, int pointer) {
        ship.touchMove(touch,pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return super.keyUp(keycode);
    }

}
