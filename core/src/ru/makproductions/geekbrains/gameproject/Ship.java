package ru.makproductions.geekbrains.gameproject;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;

public class Ship extends Sprite {

    private Rect worldBounds;
    private Vector2 speed = new Vector2();

    private boolean engineStarted;
    private float acceleration = 0.0003f;

    public Vector2 getSpeed() {
        return this.speed;
    }

    public Ship(TextureRegion region, float vx, float vy, float width,Vector2 position){
        super(region);
        speed.set(vx,vy);
        setWidthProportion(width);
        this.position.set(position);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }


    public void setEngineStarted(boolean engineStarted) {
        this.engineStarted = engineStarted;
    }

//    public void draw(SpriteBatch batch){
//
//        if(engineStarted)startEngine(batch);
//    }

    public void startEngine(SpriteBatch batch){
    }

}
