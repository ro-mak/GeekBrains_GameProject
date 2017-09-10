package ru.makproductions.geekbrains.gameproject.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private final Vector2 speed = new Vector2();
    private int damage;
    private Object owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public void setBullet(
            Object owner,
            TextureRegion region,
            Vector2 position0,
            Vector2 speed0,
            float height,
            Rect worldBounds,
            int damage
    ){
        this.owner = owner;
        regions[0] = region;
        position.set(position0);
        speed.set(speed0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float deltaTime) {
        position.mulAdd(speed,deltaTime);
        if(isOutSide(worldBounds))destroy();
    }

    public int getDamage() {
        return damage;
    }

    public Object getOwner() {
        return owner;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }
}
