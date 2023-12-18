package com.esme.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.esme.game.managers.GameStateManager;
public class EndMenu extends GameState {

    private final Texture background;

    public EndMenu(GameStateManager gsm) {
        super(gsm);
        this.background = new Texture(Gdx.files.internal("end_bg.jpg"));
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(this.background,0,0);
        sb.end();
    }

    @Override
    public void dispose() {
        this.background.dispose();
    }
}

