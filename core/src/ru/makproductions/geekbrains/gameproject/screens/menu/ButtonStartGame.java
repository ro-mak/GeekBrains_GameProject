package ru.makproductions.geekbrains.gameproject.screens.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.makproductions.geekbrains.gameproject.engine.buttons.ActionListener;
import ru.makproductions.geekbrains.gameproject.engine.buttons.ScaledTouchUpButton;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;


public class ButtonStartGame extends ScaledTouchUpButton{

    private final float BUTTON_APPEARANCE_HEIGHT = 0.1f;
    public ButtonStartGame(TextureAtlas atlas, float height, ActionListener actionListener) {
        super(atlas.findRegion("StartButton"), height, actionListener);
        setHeightProportion(height);
    }

    @Override
    public void resize(Rect worldBounds) {
        setTop(worldBounds.getTop()-BUTTON_APPEARANCE_HEIGHT);
    }
}
