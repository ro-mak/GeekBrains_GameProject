package ru.makproductions.geekbrains.gameproject.common.enemy;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.makproductions.geekbrains.gameproject.common.Bullet;
import ru.makproductions.geekbrains.gameproject.common.Collidable;
import ru.makproductions.geekbrains.gameproject.common.Ship;
import ru.makproductions.geekbrains.gameproject.common.explosions.ExplosionPool;
import ru.makproductions.geekbrains.gameproject.common.player.PlayerShip;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;

public class Enemy extends Ship {

    private PlayerShip playerShip;
    private EnemyBulletPool bulletPool;

    public Enemy() {
    }

    public void setEnemy(TextureRegion[] enemyTexture, float vx, float vy, float height,
                         EnemyBulletPool bulletPool, Rect worldBounds,
                         PlayerShip playerShip, ExplosionPool explosionPool,Sound shotSound) {
        this.shotSound = shotSound;
        regions = enemyTexture;
        fireTexture = enemyTexture[1];
        bulletTexture = enemyTexture[2];

        this.playerShip = playerShip;
        this.worldBounds = worldBounds;
        this.height = height;
        setHeightProportion(height);
        speed0.set(vx, vy / (height * 10));
        float positionX = Rnd.nextFloat(worldBounds.getLeft() + halfWidth, worldBounds.getRight() - halfWidth);
        position.set(positionX, worldBounds.getTop());
        setEngineStarted(true);

        hp = (int)(100/height);
        bulletHeight = getHalfHeight();
        bullet_margin = -(getHalfHeight());
        bulletSpeed.set(0f, -0.05f / height);
        bulletDamage = (int) (10/height);
        reloadInterval = height * 30;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    protected void shoot() {
        EnemyBullet bullet1 = bulletPool.obtain();
        bulletPosition.x = getLeft() + getHalfWidth() / 2;
        bulletPosition.y = position.y + bullet_margin;
        bullet1.setBullet(this, bulletTexture, bulletPosition, bulletSpeed,
                bulletHeight, worldBounds, bulletDamage, playerShip);
        if(shotSound.play(0.3f)==-1)throw new RuntimeException("shotSound.play()==-1");
        EnemyBullet bullet2 = bulletPool.obtain();
        bulletPosition.x = getRight() - getHalfWidth() / 2;
        bulletPosition.y = position.y + bullet_margin;
        bullet2.setBullet(this, bulletTexture, bulletPosition, bulletSpeed,
                bulletHeight, worldBounds, bulletDamage, playerShip);
        if(shotSound.play(0.3f)==-1)throw new RuntimeException("shotSound.play()==-1");
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
    public void solveCollision(Collidable collidable2) {
        if(collidable2 instanceof PlayerShip){
            destroy();
        }
        if (collidable2 instanceof Bullet){
            Bullet bullet = (Bullet) collidable2;
            if(bullet.getOwner() instanceof PlayerShip) {
                hp -= bullet.getDamage();
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        if(hp <=0)destroy();
        if (this.getBottom() <= worldBounds.getBottom()) {
            playerShip.setHp(playerShip.getHp()-bulletDamage);
            destroy();
        }

        position.mulAdd(speed0, deltaTime);
        if (playerShip.isMoving()) {
            position.x -= playerShip.getSpeed().x / 1000;
        }

        reloadTimer += deltaTime;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }
}
