package com.brujua.defenders.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

public class SpaceShip implements Entity{
    private static final float ACCELERATION_X_AXIS = 750;
    private static final float ACCELERATION_Y_AXIS = 750;
    private static final float MAX_VELOCITY_X_AXIS = 300;
    private static final float MAX_VELOCITY_Y_AXIS = 300;
    private static final long MILLISEC_BETWEEN_SHOTS = 300;
    public static final float SHIP_WIDTH = 25;
    public static final float SHIP_HEIGHT = 25;
    private Sprite shipSprite;
    private Vector2 position;
    private Vector2 velocity;
    private long timeLastShot =0;


    public SpaceShip(Vector2 position){
        this.position = new Vector2(position.x,position.y);
        velocity = new Vector2(0,0);
        shipSprite = new Sprite(new Texture(Gdx.files.internal("spaceShip.png")));
        shipSprite.setSize(SHIP_WIDTH,SHIP_HEIGHT);


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
        Vector2 velocityAux = new Vector2(velocity.x,velocity.y);
        position=position.add(velocityAux.scl(deltaTime));
    }

    public void render(Batch batch){
        batch.begin();
        shipSprite.setPosition(position.x,position.y);
        shipSprite.draw(batch);
        batch.end();
    }

    public void collied(Vector2 shipPos) {
        position=shipPos;
        velocity.set(new Vector2(0,0));
    }

    public void accelerateRight(float factor){
        if(velocity.x < MAX_VELOCITY_X_AXIS)
            velocity.x += ACCELERATION_X_AXIS * factor;
    }

    public void accelerateLeft(float factor){
        if(velocity.x > -MAX_VELOCITY_X_AXIS)
            velocity.x -= ACCELERATION_X_AXIS * factor;
    }

    public void accelerateDown(float factor){
        if(velocity.y>-MAX_VELOCITY_Y_AXIS)
            velocity.y-=ACCELERATION_Y_AXIS * factor;
    }

    public void accelerateUp(float factor){
        if(velocity.y<MAX_VELOCITY_Y_AXIS)
            velocity.y+=ACCELERATION_Y_AXIS * factor;
    }



    public void dispose() {
        shipSprite.getTexture().dispose();
    }

    public Shot fireShot(float angle) {
        if(timeLastShot == 0)
            timeLastShot = TimeUtils.nanoTime();
        long timeNow = TimeUtils.nanoTime();
        float enlapsedTime = timeNow-timeLastShot;
        //check if firing available and return corresponding shot
        if(enlapsedTime>TimeUnit.MILLISECONDS.toNanos(MILLISEC_BETWEEN_SHOTS)){
            timeLastShot = timeNow;
            return new Shot(new Vector2(position.x+SHIP_WIDTH/2,position.y+SHIP_HEIGHT/2), angle);
        }
        return null;
    }

    public Vector2 getFiringPosition() {
        return new Vector2(position.x + SHIP_WIDTH/2, position.y + SHIP_HEIGHT/2);
    }
}
