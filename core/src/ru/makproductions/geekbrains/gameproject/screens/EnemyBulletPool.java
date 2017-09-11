package ru.makproductions.geekbrains.gameproject.screens;


import ru.makproductions.geekbrains.gameproject.engine.pool.SpritesPool;

public class EnemyBulletPool extends SpritesPool<EnemyBullet> {
    @Override
    protected EnemyBullet newObject() {
        return new EnemyBullet();
    }
}
