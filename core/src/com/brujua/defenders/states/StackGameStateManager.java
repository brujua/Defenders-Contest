package com.brujua.defenders.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class StackGameStateManager {

    private Stack<State> states;
    private float screenWidth;
    private float screenHeight;


    public StackGameStateManager(float screenWidth, float screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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

}
