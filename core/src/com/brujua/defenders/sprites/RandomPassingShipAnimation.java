package com.brujua.defenders.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;


public class RandomPassingShipAnimation {

    private Texture spaceShip;
    private Vector2 shipPos;
    private Vector2 shipVel;
    private static final float SPACESHIP_VEL = 200;
    private static final double TIME_WITHOUT_SHIP = 0.5;
    private double timeOutOfView = 0.0;
    private boolean shipOutOfScreen = false;
    private float screenWidth;
    private float screenHeight;

    public RandomPassingShipAnimation(float worldWidth, float worldHeight){
        this.screenWidth = worldWidth;
        this.screenHeight = worldHeight;
        spaceShip = new Texture(Gdx.files.internal("spaceShip.png"));
        shipPos = new Vector2(0, (float) (screenHeight*1.5/3));
        shipVel = new Vector2((float) (SPACESHIP_VEL*0.75),0);

    }

    public void update(float delta){
        if(!shipOutOfScreen){
            shipPos.add(shipVel.x * delta,shipVel.y*delta);
            if(shipPos.x > screenWidth || shipPos.x <0 || shipPos.y <0 || shipPos.y > screenHeight ){
                shipOutOfScreen = true;
                timeOutOfView = 0;
            }
        }
        else {
            timeOutOfView +=delta;
            if(timeOutOfView > TIME_WITHOUT_SHIP){
                shipOutOfScreen = false;
                setRandomVelAndPosition();
            }
        }
    }

    private void setRandomVelAndPosition() {
        shipPos.x = Math.random()>0.5 ? 0 : screenWidth;
        shipPos.y = shipPos.y = (float) (100 + Math.random()*400);
        double xVel = Math.random() * SPACESHIP_VEL;
        if(shipPos.x == 0){
            shipVel.x = (float) xVel;
        }else {
            shipVel.x = (float) -xVel;
        }
        float yVel = (float) (SPACESHIP_VEL - xVel);
        shipVel.y = Math.random()>0.5 ? yVel : -yVel;
    }

    public void render(Batch batch){
        if(shipPos.x >0 && shipPos.y >=0) {
            batch.begin();
            batch.draw(spaceShip, shipPos.x, shipPos.y);
            batch.end();
        }
    }

    public void dispose() {
        spaceShip.dispose();
    }
}
