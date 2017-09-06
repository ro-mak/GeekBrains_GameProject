package ru.makproductions.geekbrains.gameproject.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.screens.menu.MenuShip;
import ru.makproductions.geekbrains.gameproject.engine.Base2DScreen;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.screens.Background;


public class GameScreen extends Base2DScreen {
    public GameScreen(Game game) {
        super(game);
    }

    private TextureAtlas atlas;
    private Background background;
    private Music music_level1;

    private MenuShip ship;
    private final float SHIP_HEIGHT = 0.4f;
    private TextureRegion[] shipTextureRegions = new TextureRegion[2];
    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        shipTextureRegions[0] = atlas.findRegion("Ship");
        shipTextureRegions[1] = atlas.findRegion("Fire");
        background = new Background(atlas.findRegion("Galaxies"));
        ship = new MenuShip(shipTextureRegions,0,0,SHIP_HEIGHT,new Vector2(0f,-0.2f));
        playMusic();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
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
        ship.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        atlas.dispose();
        music_level1.dispose();
        super.dispose();
    }
}
