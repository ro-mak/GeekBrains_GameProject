package ru.makproductions.geekbrains.gameproject.engine.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.MatrixUtils;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;

public class Button extends Sprite {

    private float originalScale;
    private boolean touched;
    public Button(TextureRegion region, float width) {
        super(region);
        position.set(0f,0.4f);
        setWidthProportion(width);
        originalScale = getScale();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        touched = true;
        setScale(getScale() - 0.1f);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(touched){
            setScale(getScale() + 0.1f);
            touched = false;
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean touchMove(Vector2 touch, int pointer) {
        setScale(originalScale);
        touched = false;
        return super.touchMove(touch, pointer);
    }
}
