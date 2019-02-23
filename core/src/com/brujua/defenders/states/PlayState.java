package com.brujua.defenders.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.brujua.defenders.entities.SpaceShip;


public class PlayState extends State {

    private Sprite background;
    private SpaceShip ship;
    private Sprite box;


    public PlayState(StackGameStateManager stateManager){
        super(stateManager);
        background = new Sprite(new Texture(Gdx.files.internal("spaceBackground2.png")));
        background.setPosition(0,0);
        background.setSize(worldWidth,worldHeight);
        /*box = new Sprite(new Texture(Gdx.files.internal("grayBox30x30.png")));*/
        ship = new SpaceShip(new Vector2(0,worldHeight/2));
    }

    @Override
    public void update(float dt) {
        handleInput();
        ship.update(dt);
        checkShipOutOfWorld();
    }

    private void checkShipOutOfWorld() {
        Vector2 shipPos = ship.getPosition();
        boolean collied=false;
        if(shipPos.x <0){
            shipPos.x = 0;
            collied = true;
        }
        if(shipPos.x > worldWidth - ship.width()){
            shipPos.x = worldWidth - ship.width();
            collied = true;
        }
        if(shipPos.y <0){
            shipPos.y = 0;
            collied = true;
        }
        if(shipPos.y > worldHeight - ship.height()){
            shipPos.y = worldHeight - ship.height();
            collied = true;
        }
        if(collied)
            ship.collied(shipPos);



    }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            stateManager.set(new MenuState(stateManager));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
       /* sb.setProjectionMatrix(cam.combined);*/
        sb.begin();
        background.draw(sb);
        /*for(int i=1;(i+1)*box.getWidth()<worldWidth;i++){
            sb.draw(box,i*box.getWidth(),0);
            sb.draw(box,i*box.getWidth(),worldHeight-box.getHeight());
        }*/
        sb.end();
        ship.render(sb);
    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
        ship.dispose();
    }
}
