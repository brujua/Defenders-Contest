package com.brujua.defenders.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public interface Entity {

    void update(float deltaTime);

    void render(Batch batch);

    Vector2 getPosition();

    float width();

    float height();

    void dispose();
}
