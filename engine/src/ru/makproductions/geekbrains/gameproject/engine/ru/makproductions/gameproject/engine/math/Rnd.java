package ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math;

import java.util.Random;

public class Rnd {
    private static final Random rnd = new Random();

    public static float nextFloat(float min, float max) {
        return rnd.nextFloat() * (max - min) + min;
    }
}
