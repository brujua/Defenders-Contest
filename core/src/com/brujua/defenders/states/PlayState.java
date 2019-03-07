package com.brujua.defenders.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.brujua.defenders.simulation.SimRenderer;
import com.brujua.defenders.simulation.Simulation;


public class PlayState extends State {

    private static final float CAMERA_ZOOM = 0.75f;
    private Sprite background;
    private SpaceShipInputProcessor inputProcessor;
    private Simulation simulation;
    private SimRenderer renderer;

    public PlayState(GameStateManager stateManager){
        super(stateManager);
        background = new Sprite(new Texture(Gdx.files.internal("spaceBackground2.png")));
        background.setPosition(0,0);
        background.setSize(worldWidth,worldHeight);

        simulation = new Simulation(worldWidth,worldHeight);
        renderer = new SimRenderer(simulation);

        inputProcessor = new SpaceShipInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);

        camera.zoom = CAMERA_ZOOM;
        camera.update();
    }

    @Override
    public void update(float dt) {
        simulation.update(dt);
        handleInput();
        inputProcessor.update(dt);
   }

    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            stateManager.set(new MenuState(stateManager));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        background.draw(sb);
        sb.end();
        renderer.render(sb,camera);
    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
        simulation.dispose();
    }

    private class SpaceShipInputProcessor extends InputAdapter{
        private static final int MAIN_SHIP_NUMBER = 0;
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
                simulation.accelerateShipUp(MAIN_SHIP_NUMBER,deltaTime);
            if(downPressed)
                simulation.accelerateShipDown(MAIN_SHIP_NUMBER,deltaTime);
            if(rightPressed)
                simulation.accelerateShipRight(MAIN_SHIP_NUMBER,deltaTime);
            if(leftPressed)
                simulation.accelerateShipLeft(MAIN_SHIP_NUMBER,deltaTime);
            if(firing || unfiredShot){
                simulation.fireShot(MAIN_SHIP_NUMBER,new Vector2(mousePos.x,mousePos.y));
                unfiredShot = false;
            }
        }
    }
}
