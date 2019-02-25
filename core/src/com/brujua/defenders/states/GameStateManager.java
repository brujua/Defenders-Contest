package com.brujua.defenders.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Class responsible of managing the different states of the game: menu, play, pause, etc.
 * This states can be stacked so that you can put one in top of another, and go back.
 * Only the top state its updated and rendered.
 */
public class GameStateManager {

    private Stack<State> states;
    private float screenWidth;
    private float screenHeight;
    private OrthographicCamera camera;


    public GameStateManager(float screenWidth, float screenHeight, OrthographicCamera camera){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.camera = camera;
        states = new Stack<>();
    }

    public void push(State state){
        states.push(state);
    }

    /**
     * Note that this method does not disposes the popped State
     * @return previous active state.
     */
    public State pop(){
        return states.pop();
    }

    /**
     * This method disposes the current state and set the new state
     *
     * @param state to replace the current state
     */
    public void set(State state){
        if(!states.empty())
            states.pop().dispose();
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void dispose() {
        while(!states.isEmpty())
            states.pop().dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
