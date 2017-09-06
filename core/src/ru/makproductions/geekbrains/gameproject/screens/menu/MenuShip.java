package ru.makproductions.geekbrains.gameproject.screens.menu;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.screens.Ship;

public class MenuShip extends Ship {

    public MenuShip(TextureRegion[] regions, float vx, float vy, float height, Vector2 position) {
        super(regions, vx, vy, height, position);
    }

    @Override
    public void update(float delta){
        if(engineStarted) {
            speed.y += acceleration;
            this.position.x += Rnd.nextFloat(-0.0001f, 0.0001f);
            this.position.y += speed.y;
        }
    }

}
