package ru.makproductions.geekbrains.gameproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ship extends Sprite {
    private boolean engineStarted;
    private float speed = 0f;
    private float acceleration = 0.3f;

    public float getSpeed() {
        return speed;
    }

    public Ship(){
        super(new Texture("Ship.png"));
        setX(250);
        setY(100);
    }

    public void setEngineStarted(boolean engineStarted) {
        this.engineStarted = engineStarted;
    }

    public void draw(SpriteBatch batch){
        batch.draw(this, getX(), getY(),Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
        if(engineStarted)startEngine(batch);
    }

    public void startEngine(SpriteBatch batch){
        batch.draw(new Texture("Fire.png"),this.getX()+20,this.getY()-150,
                Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        this.setY(this.getY()+ speed);
        speed += acceleration;
    }

}
