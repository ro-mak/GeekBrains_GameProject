package ru.makproductions.geekbrains.gameproject.screens.stars;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;
import ru.makproductions.geekbrains.gameproject.screens.game.GameShip;

public class StarsOfGame extends Sprite {

    private Rect worldBounds;
    private final Vector2 speed = new Vector2();
    private GameShip ship;
    private Vector2 shipPosition;

    public StarsOfGame(TextureRegion region, GameShip ship, float vx, float vy, float height) {
        super(region);
        this.ship = ship;
        speed.set(vx, vy);
        shipPosition = ship.position;
        setHeightProportion(height);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        position.set(posX, posY);
    }

    @Override
    public void update(float deltaTime) {
        position.mulAdd(speed, deltaTime);
        if (shipPosition.x > 0 && ship.isMoving()) {
            if(position.x < 0) {
                position.x = position.x - speed.x;
            }else if (position.x > 0){
                position.x = position.x + speed.x;
            }
        } else if (shipPosition.x < 0 && ship.isMoving()) {
            if(position.x < 0) {
                position.x = position.x + speed.x;
            }else if(position.x > 0){
                position.x = position.x - speed.x;
            }
        }
        checkAndHandleBounds();
    }

    protected void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }

}
