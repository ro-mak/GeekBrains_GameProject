package ru.makproductions.geekbrains.gameproject.common.stars;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;

public class StarsOfMenu extends Sprite {
    private final float SPAWN_HEIGHT = 0.8f;
    private Rect worldBounds;
    private final Vector2 speed = new Vector2();

    public StarsOfMenu(TextureRegion region, float vx, float vy, float height){
        super(region);
        speed.set(vx,vy);
        setHeightProportion(height);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(),worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom() + SPAWN_HEIGHT,worldBounds.getTop());
        position.set(posX,posY);
    }

    @Override
    public void update(float deltaTime) {
        position.mulAdd(speed,deltaTime);
        checkAndHandleBounds();
    }

    protected void checkAndHandleBounds(){
        if(getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if(getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if(getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if(getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
