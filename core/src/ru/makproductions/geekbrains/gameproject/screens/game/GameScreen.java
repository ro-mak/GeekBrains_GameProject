package ru.makproductions.geekbrains.gameproject.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.common.Background;
import ru.makproductions.geekbrains.gameproject.common.BulletPool;
import ru.makproductions.geekbrains.gameproject.common.CollisionDetector;
import ru.makproductions.geekbrains.gameproject.common.enemy.EnemyBulletPool;
import ru.makproductions.geekbrains.gameproject.common.enemy.EnemyFabric;
import ru.makproductions.geekbrains.gameproject.common.enemy.EnemyPool;
import ru.makproductions.geekbrains.gameproject.common.explosions.ExplosionPool;
import ru.makproductions.geekbrains.gameproject.common.stars.StarsOfGame;
import ru.makproductions.geekbrains.gameproject.engine.Base2DScreen;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;


public class GameScreen extends Base2DScreen {
    public GameScreen(Game game) {
        super(game);
    }

    private CollisionDetector collisionDetector;
    private EnemyPool enemyPool;
    private EnemyFabric enemyFabric;
    private TextureRegion[] enemyTextures = new TextureRegion[3];
    private final BulletPool bulletPool = new BulletPool();
    private final EnemyBulletPool enemyBulletPool = new EnemyBulletPool();
    private ExplosionPool explosionPool;
    private Sound soundExplosion;
    private TextureAtlas atlas;
    private Background background;
    private Music music_level1;
    private StarsOfGame[] stars;
    private final int STARS_COUNT = 250;
    private final float STARS_HEIGHT = 0.05f;
    private ru.makproductions.geekbrains.gameproject.common.player.PlayerShip playerShip;
    private final float SHIP_HEIGHT = 0.2f;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        enemyTextures[0] = atlas.findRegion("Enemy");
        enemyTextures[1] = atlas.findRegion("EnemyEngineFire");
        enemyTextures[2] = atlas.findRegion("EnemyBullet");
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("music/Explosion.wav"));
        explosionPool = new ExplosionPool(atlas,soundExplosion);
        enemyPool = new EnemyPool();
        enemyFabric = new EnemyFabric(enemyPool);
        background = new Background(atlas.findRegion("Galaxies"));
        stars = new StarsOfGame[STARS_COUNT];
        playerShip =
                new ru.makproductions.geekbrains.gameproject.common.player.PlayerShip
                        (atlas,0,0,SHIP_HEIGHT,new Vector2(0f,0f),bulletPool,explosionPool);
        playerShip.setEngineStarted(true);
        for (int i = 0; i < stars.length; i++) {
            float starHeight = STARS_HEIGHT * Rnd.nextFloat(0.3f,0.8f);
            float vx = Rnd.nextFloat(-0.0008f,0.0008f);
            float vy = Rnd.nextFloat(-0.0001f,-0.02f);
            stars[i] = new StarsOfGame(atlas.findRegion("Star"), playerShip,vx,vy,starHeight);
        }
        collisionDetector = new CollisionDetector();
        playMusic();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        playerShip.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        enemyPool.resizeActiveSprites(worldBounds);
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

    float timer;
    private void update(float delta){
        if((timer+=delta) >= 3) {
            timer = 0;
            enemyFabric.createEnemy(enemyTextures,enemyBulletPool, playerShip,explosionPool);
        }
        background.update(delta);
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        enemyBulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        playerShip.update(delta);
    }
    private void checkCollisions(){
        collisionDetector.detectCollisions();
        collisionDetector.addActiveObjects(playerShip);
        collisionDetector.addActiveObjects(enemyPool.getActiveObjects());
        collisionDetector.addActiveObjects(bulletPool.getActiveObjects());
        collisionDetector.addActiveObjects(enemyBulletPool.getActiveObjects());
    }

    private void deleteAllDestroyed(){
        collisionDetector.removeDestroyed();
        enemyBulletPool.freeAllDestroyedActiveObjects();
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    private void draw(){
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        bulletPool.drawActiveObjects(batch);
        enemyBulletPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        playerShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        atlas.dispose();
        music_level1.dispose();
        soundExplosion.dispose();
        bulletPool.dispose();
        enemyBulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        super.dispose();
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        playerShip.touchDown(touch,pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        playerShip.touchUp(touch,pointer);
    }

    @Override
    protected void touchMove(Vector2 touch, int pointer) {
        playerShip.touchMove(touch,pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        playerShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        playerShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

}
