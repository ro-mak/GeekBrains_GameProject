package ru.makproductions.geekbrains.gameproject.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;


public class Ship extends Sprite {

    private Rect worldBounds;
    protected Vector2 speed0 = new Vector2();
    protected TextureRegion fireTexture;
    protected Sprite fire;
    protected boolean engineStarted;
    protected float acceleration = 0.0003f;
    protected float height;
    public Vector2 getSpeed0() {
        return this.speed0;
    }

    public Ship(TextureRegion[] regions, float vx, float vy, float height,Vector2 position){
        super(regions[0]);
        fireTexture = regions[1];
        speed0.set(vx,vy);
        this.height = height;
        setHeightProportion(height);
        this.position.set(position);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }


    public void setEngineStarted(boolean engineStarted) {
        this.engineStarted = engineStarted;
    }

    public void draw(SpriteBatch batch){
        if(engineStarted)startEngine(batch);
        super.draw(batch);
    }

    protected void startEngine(SpriteBatch batch){
        fire = new Sprite(fireTexture);
        fire.position.y = this.getBottom() - 0.1f;
        fire.position.x = this.position.x - 0.02f + Rnd.nextFloat(-0.01f,0.01f);
        fire.setHeightProportion(height);
        fire.draw(batch);
    }

    public void update(float delta){

    }
}
