package ru.makproductions.geekbrains.gameproject.screens.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;
import ru.makproductions.geekbrains.gameproject.screens.Ship;


public class GameShip extends Ship {

    private final float BOTTOM_MARGIN = 0.1f;
    private boolean touched;
    private int sideTouched;
    private Rect worldBounds;
    private Vector2 speed = new Vector2();

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
        super.resize(worldBounds);
    }

    public GameShip(TextureRegion[] regions, float vx, float vy, float height, Vector2 position) {
        super(regions, vx, vy, height, position);
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

    private void moveLeft() {
        speed.set(speed0);
    }

    private void moveRight() {
        speed.set(speed0).rotate(180);
    }

    private void stop(){
        speed.setZero();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < 0) {
            sideTouched = -1;
        } else if (touch.x > 0) {
            sideTouched = 1;
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        sideTouched = 0;
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean touchMove(Vector2 touch, int pointer) {
        return super.touchMove(touch, pointer);
    }

    void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                break;
        }
    }

    void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                stop();
                break;
        }
    }

    private int sideOfTheScreen() {
        return sideTouched;
    }

    @Override
    public void update(float delta) {
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
        } else if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
        }
        int side = sideOfTheScreen();
        if (side == -1) {
            moveLeft();
        } else if (side == 1) {
            moveRight();
        }
        position.mulAdd(speed,delta);
    }
}
