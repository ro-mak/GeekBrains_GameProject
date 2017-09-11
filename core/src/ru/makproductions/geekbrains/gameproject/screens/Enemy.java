package ru.makproductions.geekbrains.gameproject.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;
import ru.makproductions.geekbrains.gameproject.screens.game.PlayerShip;

public class Enemy extends Ship {

    private PlayerShip playerShip;
    private EnemyBulletPool bulletPool;

    public Enemy() {
    }

    public void setEnemy(TextureRegion[] enemyTexture, float vx, float vy, float height, EnemyBulletPool bulletPool, Rect worldBounds, PlayerShip playerShip) {
        regions = enemyTexture;
        fireTexture = enemyTexture[1];
        bulletTexture = enemyTexture[2];

        this.playerShip = playerShip;
        this.worldBounds = worldBounds;
        this.height = height;
        setHeightProportion(height);
        speed0.set(vx, vy);
        float positionX = Rnd.nextFloat(worldBounds.getLeft() + halfWidth, worldBounds.getRight() - halfWidth);
        position.set(positionX, worldBounds.getTop());
        setEngineStarted(true);

        bulletHeight = getHalfHeight();
        bullet_margin = -(getHalfHeight());
        bulletSpeed.set(0f, -0.5f);
        bulletDamage = 1;
        reloadInterval = 0.75f;
        this.bulletPool = bulletPool;
    }

    @Override
    protected void shoot() {
        EnemyBullet bullet1 = bulletPool.obtain();
        bulletPosition.x = getLeft() + getHalfWidth() / 2;
        bulletPosition.y = position.y + bullet_margin;
        bullet1.setBullet(this, bulletTexture, bulletPosition, bulletSpeed,
                bulletHeight, worldBounds, bulletDamage,playerShip);

        EnemyBullet bullet2 = bulletPool.obtain();
        bulletPosition.x = getRight() - getHalfWidth() / 2;
        bulletPosition.y = position.y + bullet_margin;
        bullet2.setBullet(this, bulletTexture, bulletPosition, bulletSpeed,
                bulletHeight, worldBounds, bulletDamage,playerShip);
    }

    @Override
    protected void startEngine(SpriteBatch batch) {
        fire = new Sprite(fireTexture);
        fire.position.y = this.getTop() + getHalfWidth() / 4;
        fire.position.x = this.position.x + Rnd.nextFloat(-0.001f, 0.001f);
        fire.setHeightProportion(height);
        fire.draw(batch);
    }

    @Override
    public void update(float deltaTime) {
        position.mulAdd(speed0, deltaTime);
        if (this.isOutSide(worldBounds)) destroy();
        reloadTimer += deltaTime;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        if (playerShip.isMoving()) {
            position.x -= playerShip.getSpeed().x/1000;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }
}
