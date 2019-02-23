package com.brujua.defenders.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brujua.defenders.sprites.RandomPassingShipAnimation;

import java.util.ArrayList;
import java.util.List;

public class MenuState extends State {

    private static final String MENU_TEXT = "DEFENDERS CONTEST";
    private static final String INSTRUCTIONS = "Press enter to play...";
    private Sprite background;
    private final GlyphLayout glyphLayout = new GlyphLayout();
    private final BitmapFont font;
    private List<RandomPassingShipAnimation> shipAnimations;
    private float timePassedSinceLastShipAdded =0;


    public MenuState(StackGameStateManager stateManager) {
        super(stateManager);
        background = new Sprite(new Texture(Gdx.files.internal("spaceBackground.png")));
        background.setPosition(0,0);
        background.setSize(worldWidth,worldHeight);

        font = new BitmapFont(Gdx.files.internal("font16.fnt"), Gdx.files.internal("font16.png"), false);
        font.setColor(Color.GREEN);

        shipAnimations = new ArrayList<>();
        shipAnimations.add(new RandomPassingShipAnimation(worldWidth,worldHeight));
    }

    @Override
    public void update(float dt) {
        timePassedSinceLastShipAdded +=dt;
        if(timePassedSinceLastShipAdded >5){
            timePassedSinceLastShipAdded=0;
            shipAnimations.add(new RandomPassingShipAnimation(worldWidth,worldHeight));
        }
        for (RandomPassingShipAnimation animation : shipAnimations) {
            animation.update(dt);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            stateManager.set(new PlayState(stateManager));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        background.draw(sb);
        sb.end();
        for (RandomPassingShipAnimation animation : shipAnimations) {
            animation.render(sb);
        }
        font.getData().setScale(2f);
        glyphLayout.setText(font,MENU_TEXT);
        sb.begin();
        font.draw(sb,glyphLayout,(worldWidth /2f) - (glyphLayout.width/2), (float) (worldHeight*(2/3.0)));
        glyphLayout.setText(font,INSTRUCTIONS);
        font.getData().setScale(1f);
        font.draw(sb,glyphLayout,(worldWidth /2) - (glyphLayout.width/2),(float) (worldHeight*(1/3.0)));
        sb.end();
    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
        for (RandomPassingShipAnimation animation : shipAnimations) {
            animation.dispose();
        }
        font.dispose();
    }
}
