package com.brujua.defenders.simulation;

import com.badlogic.gdx.math.Vector2;
import com.brujua.defenders.entities.Shot;
import com.brujua.defenders.entities.SpaceShip;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final float worldWidth;
    private final float worldHeight;
    private List<SpaceShip> ships;
    private List<Shot> shots;

    public Simulation(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        SpaceShip player1 = new SpaceShip(new Vector2(0,worldHeight/2));
        SpaceShip player2 = new SpaceShip(new Vector2(worldWidth-SpaceShip.SHIP_WIDTH,worldHeight/2));
        ships = new ArrayList<>();
        ships.add(player1);
        ships.add(player2);
    }

    public void accelerateShipRight(int shipNumber, float factor){
        ships.get(shipNumber).accelerateRight(factor);
    }

    public void accelerateShipLeft(int shipNumber, float factor){
        ships.get(shipNumber).accelerateLeft(factor);
    }

    public void accelerateShipUp(int shipNumber, float factor){
        ships.get(shipNumber).accelerateUp(factor);
    }

    public void accelerateShipDown(int shipNumber, float factor){
        ships.get(shipNumber).accelerateDown(factor);
    }

    public void fireShot(int shipNumber, float angle){
        SpaceShip ship = ships.get(shipNumber);
        Shot shot = ship.fireShot(angle);
        if(shot != null)
            shots.add(shot);
    }

}
