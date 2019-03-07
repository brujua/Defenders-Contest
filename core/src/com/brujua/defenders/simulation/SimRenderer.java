package com.brujua.defenders.simulation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.brujua.defenders.entities.SpaceShip;

public class SimRenderer {

    private Simulation sim;

    public SimRenderer(Simulation sim) {
        this.sim = sim;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera){
        SpaceShip mainShip = sim.getShips().get(0);
        //Move camera with the ship and avoid showing outside the bounds of the world
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
        camera.position.x = MathUtils.clamp(mainShip.getPosition().x, effectiveViewportWidth / 2f, sim.getWorldWidth() - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(mainShip.getPosition().y, effectiveViewportHeight / 2f, sim.getWorldHeight() - effectiveViewportHeight / 2f);
        camera.update();

        sim.getShots().forEach(shot -> shot.render(batch));
        sim.getShips().forEach(spaceShip -> spaceShip.render(batch));

    }


}
