package ru.makproductions.geekbrains.gameproject.common;

import java.util.ArrayList;

import ru.makproductions.geekbrains.gameproject.engine.sprites.Sprite;

public class CollisionDetector <T extends Sprite & Collidable> {
    private ArrayList<T> sprites;
    public CollisionDetector() {
        sprites = new ArrayList<T>();
    }

    public void addActiveObjects(ArrayList<T> sprites){
        for (int i = 0; i < sprites.size(); i++) {
            T sprite = sprites.get(i);
            if(!sprite.isDestroyed()
                    &&!this.sprites.contains(sprite)) this.sprites.add(sprite);
        }
    }
    public void addActiveObjects(T sprite){
        if(!sprite.isDestroyed()&&!this.sprites.contains(sprite)) this.sprites.add(sprite);
    }

    public void removeDestroyed(){
        for (int i = 0; i <sprites.size(); i++) {
            if (sprites.get(i).isDestroyed()){
                sprites.remove(sprites.get(i));
            }
        }
    }

    public void detectCollisions(){
        for (int i = 0; i < sprites.size(); i++) {
            for (int j = 0; j < sprites.size(); j++) {
                if(!sprites.get(i).equals(sprites.get(j))){
                    if(!sprites.get(i).isOutSide(sprites.get(j))){
                        sprites.get(i).solveCollision(sprites.get(j));
                    }
                }
            }
        }
    }
}
