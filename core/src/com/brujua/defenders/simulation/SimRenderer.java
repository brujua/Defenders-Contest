package com.brujua.defenders.simulation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.brujua.defenders.entities.Shot;
import com.brujua.defenders.entities.SpaceShip;

public class SimRenderer {
    public static void render(Simulation sim, SpriteBatch batch, OrthographicCamera camera){
        SpaceShip mainShip = sim.getShips().get(0);
        //Move camera with the ship and avoid showing outside the bounds of the world
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
        camera.position.x = MathUtils.clamp(mainShip.getPosition().x, effectiveViewportWidth / 2f, sim.getWorldWidth() - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(mainShip.getPosition().y, effectiveViewportHeight / 2f, sim.getWorldHeight() - effectiveViewportHeight / 2f);
        camera.update();
        for (Shot shot : sim.getShots()) {
            shot.render(batch);
        }
        for (SpaceShip shipAux : sim.getShips())
            shipAux.render(batch);
    }
}
