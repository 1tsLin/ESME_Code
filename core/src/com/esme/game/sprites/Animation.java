package com.esme.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<Texture> textures;
    private float maxFrameTime, currentFrameTime;
    private int frameCount, frame;

    public Animation(int frameCount, float cycleTime){ //1 = standing | 2 = walking
        this.frameCount = frameCount;
        this.textures = new Array<Texture>();
        for (int i=1; i<=this.frameCount; i++){
            //this.textures.add(new Texture("character/BaseChara.png"));
            this.textures.add(new Texture(Gdx.files.internal("data/character/Walking/"+Integer.toString(i)+".png")));
        }
        this.maxFrameTime = cycleTime/this.frameCount;
        this.frame=0; //indice de la texture dans le tableau
    }

    public void update(float dt){
        this.currentFrameTime+=dt;
        if(this.currentFrameTime>this.maxFrameTime){
            this.frame++;
            this.currentFrameTime=0;
        }
        if(this.frame>=frameCount){
            this.frame=0; //si on dépasse le nbr de frame, on repasse à la première frame
        }
    }

    public Texture getTexture(){
        return this.textures.get(this.frame);
    }
}

