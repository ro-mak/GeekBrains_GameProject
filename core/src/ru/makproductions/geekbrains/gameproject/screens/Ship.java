package ru.makproductions.geekbrains.gameproject.screens;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;


public class Ship extends Sprite {

    protected BulletPool bulletPool;
    protected TextureRegion bulletTexture;
    protected Sound bulletSound;
    protected int hp;

    protected TextureRegion fireTexture;
    protected Sprite fire;

    protected Rect worldBounds;
    protected Vector2 speed0 = new Vector2();
    protected boolean engineStarted;
    protected float acceleration;
    protected float height;

    public Vector2 getSpeed0() {
        return this.speed0;
    }

    public Ship(TextureRegion shipTexture, float vx, float vy, float height,
                Vector2 position) {
        super(shipTexture);
        this.height = height;
        this.position.set(position);
        speed0.set(vx, vy);
        setHeightProportion(height);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    protected float bulletHeight;
    protected final Vector2 bulletSpeed = new Vector2();
    protected int bulletDamage;
    private final Vector2 bulletPosition = new Vector2();
    protected float reloadInterval;
    protected float reloadTimer;
    protected float BULLET_MARGIN = 0.1f;

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletPosition.x = position.x;
        bulletPosition.y = position.y + BULLET_MARGIN;
        bullet.setBullet(this, bulletTexture, bulletPosition, bulletSpeed,
                bulletHeight, worldBounds, bulletDamage);
    }

    public void setEngineStarted(boolean engineStarted) {
        this.engineStarted = engineStarted;
    }

    public void draw(SpriteBatch batch) {
        if (engineStarted) startEngine(batch);
        super.draw(batch);
    }

    protected void startEngine(SpriteBatch batch) {
    }

    public void update(float delta) {

    }
}
