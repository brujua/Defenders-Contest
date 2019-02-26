package com.brujua.defenders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.brujua.defenders.states.MenuState;
import com.brujua.defenders.states.GameStateManager;
import com.brujua.defenders.utils.AdaptiveViewPort;

public class DefendersContest extends ApplicationAdapter {
    private SpriteBatch batch;
	private GameStateManager stateManager;
	public static float WORLD_WIDHT = 800;
	public static float WORLD_HEIGHT = WORLD_WIDHT * 9/16; // 450
    private OrthographicCamera camera;
    private ScalingViewport viewPort;

    @Override
	public void create () {
		batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewPort = new AdaptiveViewPort(WORLD_WIDHT,WORLD_HEIGHT,camera);
        viewPort.apply(true);

        stateManager = new GameStateManager(WORLD_WIDHT, WORLD_HEIGHT,camera);
        stateManager.set(new MenuState(stateManager));

    }

    @Override
    public void resize(int width, int height){
        viewPort.update(width, height,true);
    }



    @Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        stateManager.update(Gdx.graphics.getDeltaTime());
		stateManager.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stateManager.dispose();
	}
}
