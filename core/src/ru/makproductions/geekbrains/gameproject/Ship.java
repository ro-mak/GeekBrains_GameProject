package ru.makproductions.geekbrains.gameproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ship extends Sprite {
    private boolean engineStarted;
    private float speed = 0f;
    private float acceleration = 0.0003f;

    public float getSpeed() {
        return speed;
    }

    public Ship(){
        super(new Texture("Ship.png"));
        setX(-0.1f);
        setY(-0.4f);
    }

    public void setEngineStarted(boolean engineStarted) {
        this.engineStarted = engineStarted;
    }

    public void draw(SpriteBatch batch){
        batch.draw(this, getX(),getY(),0.3f,0.3f);
        if(engineStarted)startEngine(batch);
    }

    public void startEngine(SpriteBatch batch){
        batch.draw(new Texture("Fire.png"),getX()-0.02f,getY() - 0.2f,0.3f,0.3f);
        this.setY(this.getY()+ speed);
        speed += acceleration;
    }

}
