package com.brujua.defenders.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


public class RandomPassingShipAnimation {

    private static final float SPACESHIP_VEL = 200; //kilometers (world unit)
    private static final double TIME_WITHOUT_SHIP = 0.5; //seconds

    private Sprite spaceShip;
    private Vector2 shipPos;
    private Vector2 shipVel;
    private double timeOutOfWorld = 0.0;
    private boolean shipOutOfWorld = false;
    private float worldWidth;
    private float worldHeight;

    public RandomPassingShipAnimation(float worldWidth, float worldHeight){
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        spaceShip = new Sprite(new Texture(Gdx.files.internal("spaceShip.png")));
        shipPos = new Vector2(0, (float) (worldHeight*1.5/3));
        shipVel = new Vector2((float) (SPACESHIP_VEL*0.75),0);

    }

    public void update(float delta){
        if(!shipOutOfWorld){
            shipPos.add(shipVel.x * delta,shipVel.y*delta);
            if(shipPos.x > worldWidth || shipPos.x <0 || shipPos.y <0 || shipPos.y > worldHeight ){
                shipOutOfWorld = true;
                timeOutOfWorld = 0;
            }
        }
        else {
            timeOutOfWorld +=delta;
            if(timeOutOfWorld > TIME_WITHOUT_SHIP){
                shipOutOfWorld = false;
                setRandomVelAndPosition();
            }
        }
    }

    private void setRandomVelAndPosition() {
        shipPos.x = Math.random()>0.5 ? 0 : worldWidth;
        shipPos.y = (float) (100 + Math.random()*400);
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
            spaceShip.setPosition(shipPos.x,shipPos.y);
            spaceShip.draw(batch);
            batch.end();
        }
    }

    public void dispose() {
        spaceShip.getTexture().dispose();
    }
}
