package com.brujua.defenders.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SpaceShip {
    private static final float ACCELERATION_X_AXIS = 750;
    private static final float ACCELERATION_Y_AXIS = 750;
    private static final float MAX_VELOCITY_X_AXIS = 300;
    private static final float MAX_VELOCITY_Y_AXIS = 300;
    private static final float SHIP_WIDTH = 25;
    private static final float SHIP_HEIGHT = 25;
    private Sprite shipTexture;
    private Vector2 position;
    private Vector2 velocity;
    private boolean upPressed,downPressed,leftPressed,rightPressed;

    public SpaceShip(Vector2 position){
        this.position = new Vector2(position.x,position.y);
        velocity = new Vector2(0,0);
        shipTexture = new Sprite(new Texture(Gdx.files.internal("spaceShip.png")));
        shipTexture.setSize(SHIP_WIDTH,SHIP_HEIGHT);
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int key){
                handleKeyDown(key);
                return true;
            }
            @Override
            public boolean keyUp(int key){
                handleKeyUp(key);
                return true;
            }
        });
    }

    public float width() {
        return SHIP_WIDTH;
    }

    public float height(){
        return SHIP_HEIGHT;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float deltaTime){
        updateVelocity(deltaTime);
        Vector2 velocityAux = new Vector2(velocity.x,velocity.y);
        position=position.add(velocityAux.scl(deltaTime));
    }

    public void render(Batch batch){
        batch.begin();
        shipTexture.setPosition(position.x,position.y);
        shipTexture.draw(batch);
        batch.end();
    }

    public void collied(Vector2 shipPos) {
        position=shipPos;
        velocity.set(new Vector2(0,0));
    }

    private void handleKeyUp(int key) {
        switch (key){
            case Input.Keys.W:{
                upPressed = false;
                break;
            }
            case Input.Keys.S:{
                downPressed = false;
                break;
            }
            case Input.Keys.A:{
                leftPressed = false;
                break;
            }
            case Input.Keys.D:{
                rightPressed = false;
                break;
            }
            default:
                break;
        }
    }

    private void handleKeyDown(int key) {
        switch (key){
            case Input.Keys.W:{
                upPressed = true;
                break;
            }
            case Input.Keys.S:{
                downPressed = true;
                break;
            }
            case Input.Keys.A:{
                leftPressed = true;
                break;
            }
            case Input.Keys.D:{
                rightPressed = true;
                break;
            }
            default:
                break;
        }
    }

    private void updateVelocity(float deltaTime) {
        if(upPressed && velocity.y<MAX_VELOCITY_Y_AXIS){
            velocity.y+=ACCELERATION_Y_AXIS * deltaTime;
        }
        if(downPressed && velocity.y>-MAX_VELOCITY_Y_AXIS){
            velocity.y-=ACCELERATION_Y_AXIS * deltaTime;
        }
        if (leftPressed && velocity.x > -MAX_VELOCITY_X_AXIS)
            velocity.x -= ACCELERATION_X_AXIS * deltaTime;
        if(rightPressed && velocity.x < MAX_VELOCITY_X_AXIS){
            velocity.x += ACCELERATION_X_AXIS * deltaTime;
        }
    }

    public void dispose() {

    }
}
