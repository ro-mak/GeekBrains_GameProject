package ru.makproductions.geekbrains.gameproject.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;


import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.MatrixUtils;
import ru.makproductions.geekbrains.gameproject.engine.ru.makproductions.gameproject.engine.math.Rect;

public class Base2DScreen implements Screen,InputProcessor {
    protected final Game game;

    protected SpriteBatch batch;
    private static final float WORLD_HEIGHT = 1;

    private  final Rect screenBounds = new Rect();
    private  final Rect worldBounds = new Rect();
    private  final Rect glBounds = new Rect(0f,0f,1f,1f);

    protected final Matrix4 matWorldToGl = new Matrix4();

    public Base2DScreen(Game game){
        this.game = game;
    }
    @Override
    public void show() {
        System.out.println("Show");
        Gdx.input.setInputProcessor(this);
        if(batch != null) throw new RuntimeException("Batch != null, dispose не вызван");
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

    }

    protected void resize(Rect worldBounds){

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize: width = " + width + " height = " + height);
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspectRatio = width / (float) height;
        worldBounds.setHeight(WORLD_HEIGHT);
        worldBounds.setWidth(WORLD_HEIGHT * aspectRatio);
        MatrixUtils.calcTransitionMatrix(matWorldToGl,worldBounds,glBounds);
        batch.setProjectionMatrix(matWorldToGl);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
        batch = null;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown");
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
