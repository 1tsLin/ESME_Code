package com.esme.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Monster {

    int PV = 50;

    private Texture texture;
    private Vector2 position;

    public Monster(float x, float y) {
        this.texture = new Texture(Gdx.files.internal("data/monsters/slime2.png"));
        this.position = new Vector2(x,y);
    }

    public Texture getTexture(){
        return this.texture;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public void dispose(){
        this.texture.dispose();
    }
}
