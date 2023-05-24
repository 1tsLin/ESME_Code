package com.esme.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esme.game.utils.Constants;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import java.util.Random;

public class Plateform {
    private Texture texture;
    private Vector2 position;

    //dans notre cas il faudrait dessiner une map entière avec différentes plateformes -> une classe par map ?
    public Plateform(float x, float y){

        this.texture=new Texture(Gdx.files.internal("plateform.png"));
        this.position = new Vector2(x,y);
    }

    public Texture getTexture(){
        return this.texture;
    }

    public Vector2 getPosition(){
        return this.position;
    }
    public float getTopY() {return this.position.y + this.texture.getHeight();}

    public float getRightX() {return this.position.x + this.texture.getWidth();}

    public void dispose(){
        this.texture.dispose();
    }

}
