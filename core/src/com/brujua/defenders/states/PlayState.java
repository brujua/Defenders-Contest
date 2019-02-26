package com.brujua.defenders.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.brujua.defenders.entities.Shot;
import com.brujua.defenders.entities.SpaceShip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PlayState extends State {

    private static final float CAMERA_ZOOM = 0.75f;
    private static final float TIME_BETWEEN_SHOTS = 0.5f;
    private Sprite background;
    private SpaceShip ship;
    private SpaceShipInputProcessor inputProcessor;
    private List<Shot> shots = new ArrayList<>();
    private float timeSinceLastShot=0;

    public PlayState(GameStateManager stateManager){
        super(stateManager);
        background = new Sprite(new Texture(Gdx.files.internal("spaceBackground2.png")));
        background.setPosition(0,0);
        background.setSize(worldWidth,worldHeight);

        ship = new SpaceShip(new Vector2(0,worldHeight/2));
        inputProcessor = new SpaceShipInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);
        camera.zoom = CAMERA_ZOOM;
        camera.update();
    }

    @Override
    public void update(float dt) {
        timeSinceLastShot +=dt;
        handleInput();
        inputProcessor.update(dt);
        ship.update(dt);
        for (Shot shot : shots) {
            shot.update(dt);
        }
        checkShipOutOfWorld();
        checkShotsOutOfWorld();
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

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            stateManager.set(new MenuState(stateManager));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        //Move camera with the ship and avoid showing outside the bounds of the world
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
        camera.position.x = MathUtils.clamp(ship.getPosition().x, effectiveViewportWidth / 2f, worldWidth - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(ship.getPosition().y, effectiveViewportHeight / 2f, worldHeight - effectiveViewportHeight / 2f);
        camera.update();

        sb.begin();
        background.draw(sb);
        sb.end();
        for (Shot shot : shots) {
            shot.render(sb);
        }
        ship.render(sb);
    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
        ship.dispose();
    }

    public void fire(float angle){
        if(timeSinceLastShot > TIME_BETWEEN_SHOTS){
            shots.add(new Shot(new Vector2(ship.getPosition().x+ship.width()/2,ship.getPosition().y+ship.height()/2),angle));
            timeSinceLastShot=0;
        }
    }

    private class SpaceShipInputProcessor extends InputAdapter{
        private boolean upPressed,downPressed,leftPressed,rightPressed,firing, unfiredShot;
        private Vector3 mousePos = new Vector3();
        private Vector3 auxVector3 = new Vector3();

        public SpaceShipInputProcessor(){
            upPressed = false;
            downPressed = false;
            leftPressed = false;
            rightPressed = false;
            firing = false;
            unfiredShot = false;
        }

        public boolean mouseMoved (int screenX, int screenY){
            mousePos = camera.unproject(auxVector3.set(screenX,screenY,0));
            //Gdx.app.log("mouse","position: "+mousePos);
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            mousePos = camera.unproject(auxVector3.set(screenX,screenY,0));
            return true;
        }

        @Override
        public boolean touchDown (int screenX, int screenY, int pointer, int button){
            if(button == Input.Buttons.LEFT && pointer<=0)
                unfiredShot = true;
                firing = true;
            return true;
        }

        @Override
        public boolean touchUp (int screenX, int screenY, int pointer, int button){
            if(button == Input.Buttons.LEFT && pointer<=0)
                firing = false;
            return true;
        }

        @Override
        public boolean keyDown(int key){
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
            return true;
        }
        @Override
        public boolean keyUp(int key){
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
            return true;
        }

        public void update(float deltaTime) {
            if(upPressed)
                ship.accelerateUp(deltaTime);
            if(downPressed)
                ship.accelerateDown(deltaTime);
            if(rightPressed)
                ship.accelerateRight(deltaTime);
            if(leftPressed)
                ship.accelerateLeft(deltaTime);
            if(firing || unfiredShot){
                Vector2 shipPos = ship.getPosition();
                Vector2 direction = new Vector2((mousePos.x - shipPos.x),(mousePos.y-shipPos.y));
                fire(direction.angle());
                unfiredShot = false;
            }
        }
    }
}
