package com.esme.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.esme.game.NameUpdate;
import com.esme.game.managers.GameStateManager;
import com.esme.game.sprites.Character;
import com.esme.game.sprites.Controller;
import com.esme.game.sprites.Monster;
import com.esme.game.sprites.Plateform;
import com.badlogic.gdx.math.Vector2;
import com.esme.game.utils.Constants;

public class LevelOne extends GameState{

    private Texture background, ground, keyTexture, exitDoorTexture;
    private Character character;
    private Plateform plateform1, plateform2, plateform3, plateform4, plateform5;
    private Array<Plateform> platforms;
    private Monster monster1, monster2;
    private Vector2 backgroundPos1, backgroundPos2, keyPosition, groundPos1, groundPos2, exitDoorPosition;
    private boolean backwards = false;
    Controller controller;
    private boolean hasKey = false;

    public LevelOne(GameStateManager gsm) {
        super(gsm);
        this.background = new Texture(Gdx.files.internal("city_bg.png"));
        this.ground = new Texture(Gdx.files.internal("bg_ground.png"));
        Constants.LEVEL_MAX_WIDTH = 5000;
        this.controller = new Controller();
        this.character = new Character(Constants.VIEWPORT_WIDTH/2-128/2, this.ground.getHeight(),this.controller);
        this.backgroundPos1 = new Vector2(this.cam.position.x - Constants.VIEWPORT_WIDTH/2, 0);
        this.backgroundPos2 = new Vector2(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 + this.background.getWidth(), 0);
        this.keyTexture = new Texture(Gdx.files.internal("data/level_one/key.png"));
        this.keyPosition = new Vector2(20, Constants.GROUND_HEIGHT);
        this.monster1= new Monster(1000,200);
        this.monster2 = new Monster(2000,400);
        this.exitDoorTexture = new Texture(Gdx.files.internal("data/level_one/door.png"));
        this.exitDoorPosition = new Vector2(4500, Constants.GROUND_HEIGHT);
        this.groundPos1 = new Vector2(this.cam.position.x - Constants.VIEWPORT_WIDTH/2, 0);
        this.groundPos2 = new Vector2(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 + this.ground.getWidth(), 0);
        this.plateform1 = new Plateform(Constants.VIEWPORT_WIDTH/2, Constants.VIEWPORT_HEIGHT/3, "data/level_one/plateform.png");
        this.plateform2 = new Plateform(Constants.VIEWPORT_WIDTH/2+450, Constants.VIEWPORT_HEIGHT/3+135, "data/level_one/plateform.png");
        this.plateform3 = new Plateform(Constants.VIEWPORT_WIDTH/2+1800+450, Constants.VIEWPORT_HEIGHT/3, "data/level_one/plateform.png");
        this.plateform4 = new Plateform(Constants.VIEWPORT_WIDTH/2+1800, Constants.VIEWPORT_HEIGHT/3+135, "data/level_one/plateform.png");
        this.plateform5 =  new Plateform(Constants.VIEWPORT_WIDTH/2+1800-450, Constants.VIEWPORT_HEIGHT/3+135, "data/level_one/plateform.png");

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
        if(this.keyPosition.x-32f/2<this.character.getPosition().x && this.character.getPosition().x<keyPosition.x+32f/2){
            hasKey=true;
            this.keyTexture.dispose();
        }
        if(this.exitDoorPosition.x-150f/2<this.character.getPosition().x && this.character.getPosition().x<exitDoorPosition.x+150f/2 && this.hasKey==true){
            this.gsm.set(new EndMenu(this.gsm));
            NameUpdate listener = new NameUpdate();
            Gdx.input.getTextInput(listener, "Veuillez entrer un pseudo", "", "Pseudo");
        }
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
            this.character.jump();
            this.cam.position.set((this.character.getPosition().x + this.character.getTexture().getWidth() / 2), Constants.VIEWPORT_HEIGHT/2, 0);
            this.cam.update();
        }
    }

    public void isradius(){
        System.out.println("position distance"+Math.abs(this.character.getPosition().x - this.monster2.getPosition().x));
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
                    System.out.println("execption"+ie);
                }
            }});

        backgroundspeed.start();
    }

    @Override
    public void update(float dt) {
        this.handleInput();
        this.updateBackground();
        this.character.update(dt, this.platforms);
    }

    private void updateBackground(){
        if(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 > this.backgroundPos1.x + this.background.getWidth()){
            this.backgroundPos1.add(0, this.background.getWidth()*2);
        }
        if(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 > this.backgroundPos1.x + this.background.getWidth()){
            this.backgroundPos2.add(0, this.background.getWidth()*2);
        }
        if(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 > this.groundPos1.x + this.ground.getWidth()){
            this.groundPos1.add(0, this.ground.getWidth()*2);
        }
        if(this.cam.position.x - Constants.VIEWPORT_WIDTH/2 > this.groundPos1.x + this.ground.getWidth()){
            this.groundPos2.add(0, this.ground.getWidth()*2);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(this.cam.combined);
        sb.begin();
        sb.draw(this.background,this.backgroundPos1.x,0);
        sb.draw(this.background,this.backgroundPos2.x,0);
        sb.draw(this.monster1.getTexture(),1000,200);
        sb.draw(this.monster2.getTexture(),2000,400);
        sb.draw(this.ground,this.groundPos1.x,0);
        sb.draw(this.ground,this.groundPos2.x,0);
        if(this.hasKey==false){
            sb.draw(this.keyTexture, this.keyPosition.x, this.keyPosition.y+30, 32f, 64f);
        }
        sb.draw(this.exitDoorTexture, this.exitDoorPosition.x, this.exitDoorPosition.y, 150f, 150f);
        for(Plateform plateform : this.platforms){
            sb.draw(plateform.getTexture(), plateform.getPosition().x, plateform.getPosition().y);
        }
        sb.draw(this.character.getTexture(), this.backwards?this.character.getPosition().x+this.character.getTexture().getWidth():this.character.getPosition().x, this.character.getPosition().y,this.backwards?-this.character.getTexture().getWidth():this.character.getTexture().getWidth(),this.character.getTexture().getHeight());
        sb.end();
        controller.draw();
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.ground.dispose();
        this.monster1.dispose();
        this.monster2.dispose();
        this.character.dispose();
        this.keyTexture.dispose();
        for(Plateform plateform : this.platforms){
            plateform.dispose();
        }
    }
}
