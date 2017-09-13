package ru.makproductions.geekbrains.gameproject.common.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.common.Bullet;
import ru.makproductions.geekbrains.gameproject.common.BulletPool;
import ru.makproductions.geekbrains.gameproject.common.Collidable;
import ru.makproductions.geekbrains.gameproject.common.enemy.Enemy;
import ru.makproductions.geekbrains.gameproject.common.explosions.ExplosionPool;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;
import ru.makproductions.geekbrains.gameproject.common.Ship;


public class PlayerShip extends Ship {

    private static final int INVALID_POINTER = -1;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;
    private boolean isMoving;

    public boolean isMoving() {
        return isMoving;
    }

    private final float BOTTOM_MARGIN = 0.1f;
    private Vector2 speed = new Vector2();

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    public PlayerShip(TextureAtlas atlas, float vx, float vy, float height,
                      Vector2 position, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("Ship"), vx, vy, height, position);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        fireTexture = atlas.findRegion("Fire");
        bulletSpeed.set(0f,0.5f);
        bulletDamage = 500;
        bulletTexture = atlas.findRegion("PlayerBullet");
        bulletHeight = 0.05f;
        reloadInterval = 0.25f;
        hp = 5000;
    }

    @Override
    protected void startEngine(SpriteBatch batch) {
        fire = new Sprite(fireTexture);
        fire.position.y = this.getBottom() - 0.05f;
        fire.position.x = this.position.x - 0.02f + Rnd.nextFloat(-0.001f, 0.001f);
        fire.setHeightProportion(height);
        fire.draw(batch);
        this.speed0.x = -0.5f;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    private void moveLeft() {
        speed.set(speed0);
        isMoving = true;
    }

    private void moveRight() {
        speed.set(speed0).rotate(180);
        isMoving = true;
    }

    private void stop() {
        speed.setZero();
        isMoving = false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.position.x) {
            if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        } else if (touch.x > worldBounds.position.x) {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) moveRight();
            else stop();
        } else if (pointer == rightPointer) {

            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) moveLeft();
            else stop();
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean touchMove(Vector2 touch, int pointer) {
        return super.touchMove(touch, pointer);
    }

    private boolean pressedLeft;
    private boolean pressedRight;


    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) moveRight();
                else stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) moveLeft();
                else stop();
                break;
        }
    }
    private int previousHp;
    @Override
    public void update(float delta) {
        if(previousHp!=hp)System.out.println("Player hp left:" + hp);
        previousHp = hp;
        if(hp <= 0 && !isDestroyed()) destroy();
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        } else if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        position.mulAdd(speed, delta);
        reloadTimer  += delta;
        if(reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
    }

    @Override
    public void solveCollision(Collidable collidable2) {
        if(collidable2 instanceof Enemy){
            destroy();
        }else if(collidable2 instanceof Bullet){
            Bullet bullet = (Bullet) collidable2;
            if(bullet.getOwner()!=this) {
                hp -= bullet.getDamage();
            }
        }

    }
}