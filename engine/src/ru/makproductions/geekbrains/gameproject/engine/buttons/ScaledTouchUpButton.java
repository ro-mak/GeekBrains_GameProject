package ru.makproductions.geekbrains.gameproject.engine.buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.MatrixUtils;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;

public class ScaledTouchUpButton extends Sprite {

    private int pointer;
    private final ActionListener actionListener;
    private float originalScale;
    private boolean touched;

    public ScaledTouchUpButton(TextureRegion region, float height,ActionListener actionListener) {
        super(region);
        this.actionListener = actionListener;
        setHeightProportion(height);
        originalScale = getScale();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(touched || !isMe(touch)) return  false;
        touched = true;
        this.pointer = pointer;
        setScale(getScale() - 0.1f);
        return true;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if(!touched || this.pointer != pointer)return false;
        touched = false;
        scale = 1f;
        if(isMe(touch)) {
            setScale(getScale() + 0.1f);
            actionListener.actionPerformed(this);
        }

        return true;
    }

    @Override
    public boolean touchMove(Vector2 touch, int pointer) {
        return false;
    }
}
