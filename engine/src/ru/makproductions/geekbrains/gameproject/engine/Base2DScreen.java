package ru.makproductions.geekbrains.gameproject.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;


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

    protected final Matrix3 matScreenToWorld = new Matrix3();
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
        MatrixUtils.calcTransitionMatrix(matScreenToWorld,screenBounds,worldBounds);
        resize(worldBounds);
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



    protected void touchDown(Vector2 touch, int pointer) {

    }
    protected void touchUp(Vector2 touch, int pointer) {

    }
    protected void touchMove(Vector2 touch, int pointer) {

    }

    private final Vector2 touch = new Vector2();

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,revertY(screenY)).mul(matScreenToWorld);
        touchDown(touch,pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,revertY(screenY)).mul(matScreenToWorld);
        touchUp(touch,pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX,revertY(screenY)).mul(matScreenToWorld);
        touchMove(touch,pointer);
        return false;
    }

    protected float revertY(int screenY){
        return screenBounds.getHeight() - 1f - screenY;
    }

    @Override
    public void render(float delta) {

    }

    protected void resize(Rect worldBounds){

    }

    //region Extra
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
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
    //endregion
}
