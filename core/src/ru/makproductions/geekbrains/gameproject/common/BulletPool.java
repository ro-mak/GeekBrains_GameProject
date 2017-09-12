package ru.makproductions.geekbrains.gameproject.common;

import ru.makproductions.geekbrains.gameproject.engine.pool.SpritesPool;


public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected void debugLog() {
//        System.out.println("BulletPool change active/free: "
//                + activeObjects.size() + " / "
//                + freeObjects.size());
    }

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
