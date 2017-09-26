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
import ru.makproductions.geekbrains.gameproject.common.player.PlayerShip;
import ru.makproductions.geekbrains.gameproject.common.stars.StarsOfGame;
import ru.makproductions.geekbrains.gameproject.engine.Base2DScreen;
import ru.makproductions.geekbrains.gameproject.engine.Font;
import ru.makproductions.geekbrains.gameproject.engine.Sprite2DTexture;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;


public class GameScreen extends Base2DScreen {
    public GameScreen(Game game) {
        super(game);
    }

    private CollisionDetector collisionDetector;

    private EnemyPool enemyPool;
    private EnemyFabric enemyFabric;
    private TextureRegion[] enemyTextures = new TextureRegion[4];

    private final EnemyBulletPool enemyBulletPool = new EnemyBulletPool();

    private final BulletPool bulletPool = new BulletPool();
    private ExplosionPool explosionPool;

    private Music music_level1;
    private Sound soundExplosion;
    private Sound playerShotSound;
    private Sound enemyShotSound;

    private TextureAtlas gameAtlas;
    private Sprite2DTexture textureBackground;
    private Background background;
    private Font font;


    private StarsOfGame[] stars;
    private final int STARS_COUNT = 50;
    private final float STARS_HEIGHT = 0.05f;
    private PlayerShip playerShip;
    private final float SHIP_HEIGHT = 0.15f;

    @Override
    public void show() {
        super.show();
        font = new Font("fonts/font.fnt","fonts/font.png");
        font.setWorldSize(0.05f);
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/Explosion.wav"));
        playerShotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/PlayerShot.wav"));
        enemyShotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/EnemyShot.wav"));
        gameAtlas = new TextureAtlas("textures/gameAtlas.pack");
        textureBackground = new Sprite2DTexture("textures/Galaxies.png");
        background = new Background(new TextureRegion(textureBackground));
        stars = new StarsOfGame[STARS_COUNT];
        explosionPool = new ExplosionPool(gameAtlas, soundExplosion);
        enemyPool = new EnemyPool();
        enemyFabric = new EnemyFabric(enemyPool);
        playerShip =
                new PlayerShip
                        (gameAtlas, 0, 0, SHIP_HEIGHT, new Vector2(0f, 0f), bulletPool, explosionPool, playerShotSound);
        playerShip.setEngineStarted(true);
        for (int i = 0; i < stars.length; i++) {
            float starHeight = STARS_HEIGHT * Rnd.nextFloat(0.3f, 0.8f);
            float vx = Rnd.nextFloat(-0.0008f, 0.0008f);
            float vy = Rnd.nextFloat(-0.0001f, -0.02f);
            stars[i] = new StarsOfGame(gameAtlas.findRegion("Star"), playerShip, vx, vy, starHeight);
        }
        collisionDetector = new CollisionDetector();
        enemyTextures[0] = gameAtlas.findRegion("Enemy");
        enemyTextures[1] = gameAtlas.findRegion("EnemyEngineFire");
        enemyTextures[2] = gameAtlas.findRegion("EnemyBullet");
        enemyTextures[3] = gameAtlas.findRegion("EnemyShipDamaged");

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

    private void playMusic() {
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

    private void update(float delta) {
        if ((timer += delta) >= 3) {
            timer = 0;
            enemyFabric.createEnemy(enemyTextures, enemyBulletPool, playerShip, explosionPool, enemyShotSound);
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

    private void checkCollisions() {
        collisionDetector.detectCollisions();
        collisionDetector.addActiveObjects(playerShip);
        collisionDetector.addActiveObjects(enemyPool.getActiveObjects());
        collisionDetector.addActiveObjects(bulletPool.getActiveObjects());
        collisionDetector.addActiveObjects(enemyBulletPool.getActiveObjects());
    }

    private void deleteAllDestroyed() {
        collisionDetector.removeDestroyed();
        enemyBulletPool.freeAllDestroyedActiveObjects();
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
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
        font.draw(batch,"Frags: " + playerShip.getFrags(),worldBounds.getLeft(),worldBounds.getTop());
        font.draw(batch,"HP: " + playerShip.getHp(),worldBounds.getRight() - 0.25f,worldBounds.getTop());
        batch.end();
    }

    @Override
    public void dispose() {
        gameAtlas.dispose();
        music_level1.dispose();
        soundExplosion.dispose();
        playerShotSound.dispose();
        enemyShotSound.dispose();
        bulletPool.dispose();
        enemyBulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        super.dispose();
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        playerShip.touchDown(touch, pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        playerShip.touchUp(touch, pointer);
    }

    @Override
    protected void touchMove(Vector2 touch, int pointer) {
        playerShip.touchMove(touch, pointer);
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
