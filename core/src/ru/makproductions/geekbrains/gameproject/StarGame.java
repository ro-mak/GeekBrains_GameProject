package ru.makproductions.geekbrains.gameproject;

import com.badlogic.gdx.Game;

import ru.makproductions.geekbrains.gameproject.screens.menu.MenuScreen;

public class StarGame extends Game {
    @Override
    public void create(){
        setScreen(new MenuScreen(this));
    }
}
