package ru.makproductions.geekbrains.gameproject.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rnd;
import ru.makproductions.geekbrains.gameproject.screens.game.PlayerShip;

public class EnemyFabric {
    private EnemyPool enemyPool;
    private Enemy enemy;

    public EnemyFabric(EnemyPool enemyPool) {
        this.enemyPool = enemyPool;
    }

    public void createEnemy(TextureRegion[] enemyTexture, EnemyBulletPool bulletPool, PlayerShip playerShip) {
        float vy = Rnd.nextFloat(-0.1f, -0.3f);
        float height = Rnd.nextFloat(0.05f, 0.1f);
        enemy = enemyPool.obtain();
        if (enemy == null) throw new RuntimeException("trying to invoke method setEnemy to null");
        enemy.setEnemy(enemyTexture, 0f, vy, height, bulletPool, enemyPool.getWorldBounds(),playerShip);
    }
}
