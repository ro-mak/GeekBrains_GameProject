package ru.makproductions.geekbrains.gameproject.common.enemy;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;
import ru.makproductions.geekbrains.gameproject.common.Bullet;
import ru.makproductions.geekbrains.gameproject.common.player.PlayerShip;

public class EnemyBullet extends Bullet {
    private PlayerShip playerShip;

    public void setBullet(Object owner, TextureRegion region, Vector2 position0, Vector2 speed0, float height, Rect worldBounds, int damage,PlayerShip playerShip) {
        super.setBullet(owner, region, position0, speed0, height, worldBounds, damage);
        this.playerShip = playerShip;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (playerShip.isMoving()) {
            position.x -= playerShip.getSpeed().x/1000;
        }
    }
}
