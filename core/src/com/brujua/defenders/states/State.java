package com.brujua.defenders.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class State {

    protected float worldWidth;
    protected float worldHeight;
    protected GameStateManager stateManager;
    protected OrthographicCamera camera;
/*    protected OrthographicCamera cam;*/
    protected Vector2 mouse;

    protected State(GameStateManager stateManager){
        this.stateManager = stateManager;
        worldWidth = stateManager.getScreenWidth();
        worldHeight = stateManager.getScreenHeight();
        camera = stateManager.getCamera();
      /*  cam = new OrthographicCamera();
        cam.setToOrtho(false, stateManager.getScreenWidth(), stateManager.getScreenHeight());*/
    }

    /*public abstract void handleInput();*/

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();

}
