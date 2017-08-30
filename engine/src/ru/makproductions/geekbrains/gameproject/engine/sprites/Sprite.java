package ru.makproductions.geekbrains.gameproject.engine.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int currentFrame;

    public Sprite(TextureRegion region){
        if(region == null)
            throw new RuntimeException("Created Sprite with null region");
        regions = new TextureRegion[1];
        regions[0] = region;

    }

    public void draw(SpriteBatch batch) {
        batch.draw(regions[currentFrame],
                getLeft(),getBottom(),
                halfWidth,halfHeight,
                getWidth(),getHeight(),
                scale,scale,angle);
    }

    public void setWidthProportion(float width){
        setWidth(width);
        float aspectRatio = regions[currentFrame].getRegionWidth()
                / (float)regions[currentFrame].getRegionHeight();
        setHeight(width/aspectRatio);
    }

    public void setHeightProportion(float height){
        setHeight(height);
        float aspectRatio = regions[currentFrame].getRegionWidth()
                / (float)regions[currentFrame].getRegionHeight();
        setWidth(height * aspectRatio);
    }

    protected boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }
    protected boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }
    protected boolean touchMove(Vector2 touch, int pointer) {
        return false;
    }

    public void update(float deltaTime){

    }

    public void resize(Rect worldBounds){

    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "Sprite: " + " angle = " + angle + " scale = " + scale + " " + super.toString();
    }
}
