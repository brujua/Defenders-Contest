package com.brujua.defenders.simulation;

import com.badlogic.gdx.math.Vector2;
import com.brujua.defenders.entities.Shot;
import com.brujua.defenders.entities.SpaceShip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulation {
    private final float worldWidth;
    private final float worldHeight;
    private List<SpaceShip> ships;
    private List<Shot> shots;

    public Simulation(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        //in middle screen left
        SpaceShip player1 = new SpaceShip(new Vector2(0,worldHeight/2));
        //in middle screen right
        SpaceShip player2 = new SpaceShip(new Vector2(worldWidth-SpaceShip.SHIP_WIDTH,worldHeight/2));
        ships = new ArrayList<>();
        ships.add(player1);
        ships.add(player2);

        shots = new ArrayList<>();
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

    public void fireShot(int shipNumber, Vector2 target){
        SpaceShip ship = ships.get(shipNumber);
        Vector2 shipPos = ship.getFiringPosition();
        Vector2 direction = new Vector2((target.x - shipPos.x),(target.y-shipPos.y));
        Shot shot = ship.fireShot(direction.angle());
        if(shot != null) {
            shots.add(shot);
        }
    }

    public void update(float deltaTime){
        for (SpaceShip ship : ships)
            ship.update(deltaTime);
        for (Shot shot : shots)
            shot.update(deltaTime);
        checkCollisions();
    }

    private void checkCollisions(){
        for(SpaceShip ship: ships)
            checkShipOutOfWorld(ship);
        checkShotsOutOfWorld();

    }

    private void checkShipOutOfWorld(SpaceShip ship) {
        Vector2 shipPos = ship.getPosition();
        boolean collied = false;
        if (shipPos.x < 0) {
            shipPos.x = 0;
            collied = true;
        }
        if (shipPos.x > worldWidth - ship.width()) {
            shipPos.x = worldWidth - ship.width();
            collied = true;
        }
        if (shipPos.y < 0) {
            shipPos.y = 0;
            collied = true;
        }
        if (shipPos.y > worldHeight - ship.height()) {
            shipPos.y = worldHeight - ship.height();
            collied = true;
        }
        if (collied)
            ship.collied(shipPos);

    }

    private void checkShotsOutOfWorld() {
        Iterator<Shot> iterator = shots.iterator();
        while (iterator.hasNext()){
            Shot shot = iterator.next();
            Vector2 shotPos = shot.getPosition();
            if(shotPos.x<0 || shotPos.y<0 || shotPos.x>worldWidth || shotPos.y>worldWidth){
                shot.dispose();
                iterator.remove();
            }
        }
    }

    public List<Shot> getShots(){
        return shots;
    }

    public List<SpaceShip> getShips(){
        return ships;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public void dispose() {
        shots.forEach(Shot::dispose);
        ships.forEach(SpaceShip::dispose);
    }
}
