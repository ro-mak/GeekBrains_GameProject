package ru.makproductions.geekbrains.gameproject.screens.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;

public class Background  extends Sprite{
    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        position.set(worldBounds.position);
    }
}
