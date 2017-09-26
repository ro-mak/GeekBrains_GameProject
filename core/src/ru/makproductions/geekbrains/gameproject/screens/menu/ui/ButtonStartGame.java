package ru.makproductions.geekbrains.gameproject.screens.menu.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.Font;
import ru.makproductions.geekbrains.gameproject.engine.buttons.ActionListener;
import ru.makproductions.geekbrains.gameproject.engine.buttons.ScaledTouchUpButton;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;


public class ButtonStartGame extends ScaledTouchUpButton{

    private static Font font = new Font("fonts/font.fnt","fonts/font.png");

    private final float BUTTON_APPEARANCE_HEIGHT = 0.1f;

    public ButtonStartGame(float height, ActionListener actionListener) {
        super(height, actionListener);
        regions = new TextureRegion[1];
        regions[0] = font.getRegion();
        setHeightProportion(height);
        font.setWorldSize(0.1f);
        scaleSize = 0.0003f;
        font.getData().setScale(0.001f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch,"Start! ", position.x - 0.2f,position.y);
    }

    @Override
    protected void scaleUp() {
       font.getData().setScale(font.getScaleX()+scaleSize,font.getScaleY()+scaleSize);
    }

    @Override
    protected void scaleDown() {
        font.getData().setScale(font.getScaleX()-scaleSize,font.getScaleY()-scaleSize);
    }

    @Override
    protected void returnScale() {
        font.getData().setScale(0.001f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setTop(worldBounds.getTop()-BUTTON_APPEARANCE_HEIGHT);
    }
}
