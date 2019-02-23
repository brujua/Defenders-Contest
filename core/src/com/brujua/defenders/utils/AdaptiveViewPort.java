package com.brujua.defenders.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

/**
 * An adaptive scaling viewport that changes the scaling strategy
 * depending on the relation between the screen's and the world's dimensions aspects ratio.
 */
public class AdaptiveViewPort extends ScalingViewport {


    private static final float MAX_ASPECT_RATIO_DIFFERENCE = 0.2f;

    private AdaptiveViewPort(Scaling scaling, float worldWidth, float worldHeight, Camera camera) {
        super(scaling, worldWidth, worldHeight, camera);
    }

    public AdaptiveViewPort(float worldWidth, float worldHeight, Camera camera){
        this(Scaling.fill,worldWidth,worldHeight,camera);
        float aspectRatio =  Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        float worldAspectRatio = worldWidth / worldHeight;
        if(worldAspectRatio != aspectRatio)
            if(isAspectDifferenceTolerable(aspectRatio))
                setScaling(Scaling.stretch);
            else
                setScaling(Scaling.fit);
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        float newAspectRatio = screenWidth/(float)screenHeight;
        if(getWorldAspectRatio() == newAspectRatio){
            setScaling(Scaling.fill);
            super.update(screenWidth, screenHeight, centerCamera);
        } else {
            if(isAspectDifferenceTolerable(newAspectRatio))
                setScaling(Scaling.stretch);
            else
                setScaling(Scaling.fit);
        }
        super.update(screenWidth, screenHeight, centerCamera);
    }

    /**
     * @return if the newAspectRatio its too different of the aspectRatio of the internal world width height
     * and thus a different scaling its needed.
     */
    private boolean isAspectDifferenceTolerable(float newAspectRatio) {
        return Math.abs(newAspectRatio - getWorldAspectRatio()) < MAX_ASPECT_RATIO_DIFFERENCE;

    }

    public float getWorldAspectRatio(){
        return getWorldWidth() / getWorldHeight();
    }
}
