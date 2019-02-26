package com.brujua.defenders.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Shot implements Entity {

    private static final float VELOCITY = 350;
    private Vector2 position;
    private Vector2 velocity;
    private static final float WIDTH=18;
    private static final float HEIGHT=6;
    private Sprite sprite;

    public Shot(Vector2 position, float angle){
        this.position = new Vector2(position.x,position.y);
        velocity = new Vector2(1,0);
        velocity.setAngle(angle);
        velocity = velocity.scl(VELOCITY, VELOCITY);
        sprite = new Sprite(new Texture(Gdx.files.internal("simpleShot.png")));
        sprite.setPosition(position.x,position.y);
        sprite.rotate(angle);
        sprite.setSize(WIDTH,HEIGHT);
    };

    @Override
    public void update(float deltaTime) {
        Vector2 velAux = new Vector2(velocity.x,velocity.y);
        position = position.add(velAux.scl(deltaTime));
    }

    @Override
    public void render(Batch batch) {
        batch.begin();
        sprite.setPosition(position.x,position.y);
        sprite.draw(batch);
        batch.end();

    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public float width() {
        return WIDTH;
    }

    @Override
    public float height() {
        return HEIGHT;
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
