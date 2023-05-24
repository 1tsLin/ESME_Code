package com.esme.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esme.game.utils.Constants;

import javax.annotation.processing.SupportedSourceVersion;
import javax.naming.ldap.Control;

public class Character {
    private Texture texture;
    private Vector2 position, fall;
    private Animation walking;
    Controller controller;
    boolean collisionDetected;
    float value = 181;
    public Character(float x, float y, Controller controller){
        this.controller=controller;
        this.texture = new Texture(Gdx.files.internal("data/character/basechara.png"));
        this.position = new Vector2(x,y);
        this.fall = new Vector2(0,0);
        this.walking = new Animation(8,0.8f); //on a pas les différentes animations du sprite :eyes:
    }

    public void update(float dt, Array<Plateform> platforms){
        this.fall.add(0, Constants.GRAVITY);
        this.fall.scl(dt); //multiplie les valeurs du vecteur fall par dt afin d'avoir une chute plus naturelle
        this.position.add(this.fall);
        this.fall.scl(1/dt); //remet les bonnes coordonnées

        if(this.position.y <181){ //pour qu'il soit sur le sol
            this.position.y=181;
            this.fall.y=0;
        }

        if(this.position.x<0){
            this.position.x=0;
        }

        if(value !=181 && this.position.y <=value){ //pour qu'il soit sur la plateforme
            this.position.y=value;
            this.fall.y=0;
        }
        if(this.position.x>3840-this.texture.getWidth()){
            this.position.x=3840-this.texture.getWidth();
        }

        for(Plateform plateform : platforms){
            collisionDetected = false;
            if((this.position.x+128/2 > plateform.getPosition().x-128/4 && this.position.x< plateform.getRightX()-128/3) &&
                    (this.position.y >= plateform.getTopY() && this.position.y < plateform.getTopY()+20) && (this.fall.y<=0)){
                this.fall.y=0;
                this.position.y=plateform.getTopY();
                collisionDetected = true;
                value = plateform.getTopY();
                break;
            }
        }
        if (collisionDetected==false) {
            value=181;
        }

        if(!this.controller.isLeftPressed() && !this.controller.isRightPressed() && this.fall.y==0){
            //!Gdx.input.isKeyPressed(Input.Keys.LEFT)&& !Gdx.input.isKeyPressed(Input.Keys.RIGHT)
            //si le personnage ne bouge pas, on lui remet sa texture de base
            this.texture = new Texture(Gdx.files.internal("data/character/basechara.png"));
        }
        if(this.fall.y>0){
            //si le perso monte = saute
            this.texture=new Texture(Gdx.files.internal("data/character/Jump.png"));
        }

        if(this.fall.y<0){
            //si le perso chute
            this.texture=new Texture(Gdx.files.internal("data/character/Fall.png"));
        }
    }

    public void moveRight(){
        this.position.x+=Constants.CHAR_SPEED;
        this.texture = this.walking.getTexture();
        this.walking.update(Gdx.graphics.getDeltaTime());
    }

    public void moveLeft(){
        this.position.x-=Constants.CHAR_SPEED;
        this.texture = this.walking.getTexture();
        this.walking.update(Gdx.graphics.getDeltaTime());
    }

    public void jump(){
        if(this.fall.y==0){
        this.fall.y = 1000;}
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
