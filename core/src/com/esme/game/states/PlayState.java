package com.esme.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.esme.game.NameUpdate;
import com.esme.game.Score;
import com.esme.game.managers.GameStateManager;
import com.esme.game.sprites.Controller;
import com.esme.game.sprites.Monster;
import com.esme.game.sprites.Plateform;
import com.esme.game.utils.Constants;
import com.esme.game.sprites.Character;

import java.util.concurrent.TimeUnit;

import javax.tools.JavaFileManager;

import jdk.tools.jlink.internal.Platform;


public class PlayState extends GameState{

    private Texture background, ground;
    private Character character;
    private Monster monster1, monster2;
    private Plateform plateform1, plateform2, plateform3, plateform4, plateform5;
    private Array<Plateform> platforms;

    private boolean backwards = false;
    Controller controller;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        this.background = new Texture(Gdx.files.internal("bg.jpg"));
        this.ground = new Texture(Gdx.files.internal("bg_ground.png"));

        this.controller = new Controller();
        this.monster1= new Monster(1000,200);

        this.monster2 = new Monster(2000,400);
        this.character = new Character(Constants.VIEWPORT_WIDTH/2-128/2, this.ground.getHeight(),this.controller);

        this.plateform1 = new Plateform(Constants.VIEWPORT_WIDTH/2, Constants.VIEWPORT_HEIGHT/3, "plateform.png");
        this.plateform2 = new Plateform(Constants.VIEWPORT_WIDTH/2+450, Constants.VIEWPORT_HEIGHT/3+135, "plateform.png");
        this.plateform3 = new Plateform(Constants.VIEWPORT_WIDTH/2+1800+450, Constants.VIEWPORT_HEIGHT/3, "plateform.png");
        this.plateform4 = new Plateform(Constants.VIEWPORT_WIDTH/2+1800, Constants.VIEWPORT_HEIGHT/3+135, "plateform.png");
        this.plateform5 =  new Plateform(Constants.VIEWPORT_WIDTH/2+1800-450, Constants.VIEWPORT_HEIGHT/3+135, "plateform.png");

        this.platforms = new Array<>();
        this.platforms.add(this.plateform1);
        this.platforms.add(this.plateform2);
        this.platforms.add(this.plateform3);
        this.platforms.add(this.plateform4);
        this.platforms.add(this.plateform5);

        this.cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

    }

    @Override
    protected void handleInput() {
        isradius();
        if(controller.isRightPressed()){
            this.character.moveRight();
            this.backwards=false;
            this.cam.position.set((this.character.getPosition().x + this.character.getTexture().getWidth() / 2), Constants.VIEWPORT_HEIGHT/2, 0);
            this.cam.update();
        }
        if(controller.isLeftPressed()){
            this.character.moveLeft();
            this.backwards=true;
            this.cam.position.set((this.character.getPosition().x + this.character.getTexture().getWidth() / 2), Constants.VIEWPORT_HEIGHT/2, 0);
            this.cam.update();
        }
        if(controller.isUpPressed()){
            Gdx.app.log("Position", String.valueOf(this.character.getPosition().x)+" et "+ String.valueOf(this.character.getPosition().y));
            this.character.jump();
            this.cam.position.set((this.character.getPosition().x + this.character.getTexture().getWidth() / 2), Constants.VIEWPORT_HEIGHT/2, 0);
            this.cam.update();
            if(1556.0<this.character.getPosition().x && this.character.getPosition().x<1756.0 && this.character.getPosition().y>(Constants.GROUND_HEIGHT+20)){
                this.gsm.set(new LevelOne(this.gsm));
            }
        }

    }
    public void isradius(){
if(Math.abs(this.character.getPosition().x - this.monster1.getPosition().x) <150 && Math.abs(this.character.getPosition().y - this.monster1.getPosition().y ) <150 ){
    Constants.CHAR_SPEED= 4;
    this.resetspeed();
} else if (Math.abs(this.character.getPosition().x - this.monster2.getPosition().x) <150 && Math.abs(this.character.getPosition().y - this.monster2.getPosition().y) <150) {
    Constants.CHAR_SPEED= 4;
    this.resetspeed();
}
    }
    public void resetspeed(){
        Thread backgroundspeed = new Thread( new Runnable() {
            @Override
            public void run(){
                try {
                    Thread.sleep(4000);
                    Constants.CHAR_SPEED = 8;
                } catch (InterruptedException ie) {
                    System.out.println("exception"+ie);
                }
            }});

        backgroundspeed.start();
    }

    @Override
    public void update(float dt) {
        this.handleInput();
        this.character.update(dt, this.platforms);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(this.cam.combined);
        sb.begin();
        sb.draw(this.background,0,0);
        sb.draw(this.monster1.getTexture(),1000,200);
        sb.draw(this.monster2.getTexture(),2000,400);
        sb.draw(this.ground,0,0);
        for(Plateform plateform : this.platforms){
            sb.draw(plateform.getTexture(), plateform.getPosition().x, plateform.getPosition().y);
        }
        sb.draw(this.character.getTexture(), this.backwards?this.character.getPosition().x+this.character.getTexture().getWidth():this.character.getPosition().x, this.character.getPosition().y,this.backwards?-this.character.getTexture().getWidth():this.character.getTexture().getWidth(),this.character.getTexture().getHeight());
        //? fonctionne comme une condition if - traitement condition remplie : else condition non remplie
        sb.end();
        controller.draw();
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.ground.dispose();
        this.character.dispose();
        this.monster1.dispose();
        this.monster2.dispose();
        for(Plateform plateform : this.platforms){
            plateform.dispose();
        }
    }

}
