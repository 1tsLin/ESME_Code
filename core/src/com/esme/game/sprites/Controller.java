package com.esme.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esme.game.LaunchGame;
import com.esme.game.utils.Constants;
import com.esme.game.LaunchGame;


public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed;
    OrthographicCamera cam;

    public Controller(){
        cam = new OrthographicCamera();
        viewport = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, cam);
        stage = new Stage(viewport, LaunchGame.sb);
        Gdx.input.setInputProcessor(stage);

        Table tableLeft = new Table();
        tableLeft.setFillParent(true);
        tableLeft.left().bottom();

        Table tableRight = new Table();
        tableRight.setFillParent(true);
        tableRight.bottom().right();

        Image upImg = new Image(new Texture(Gdx.files.internal("up.png")));
        upImg.setSize(100,100);
        Image rightImg = new Image(new Texture(Gdx.files.internal("right.png")));
        rightImg.setSize(100,100);
        Image leftImg = new Image(new Texture(Gdx.files.internal("left.png")));
        leftImg.setSize(100,100);

        upImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                upPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                upPressed = false;
            }
        });

        rightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                rightPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                rightPressed = false;
            }
        });

        leftImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                leftPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                leftPressed = false;
            }
        });

        tableLeft.padLeft(20);
        tableLeft.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        tableLeft.row().pad(5,5,5,5);
        tableLeft.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        tableLeft.add().width(50);
        tableLeft.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        tableLeft.padBottom(25);

        tableRight.padRight(20);
        tableRight.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        tableRight.padBottom(25);


        stage.addActor(tableLeft);
        stage.addActor(tableRight);
    }
    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed(){
        return upPressed;
    }

    public boolean isRightPressed(){
        return rightPressed;
    }

    public boolean isLeftPressed(){
        return leftPressed;
    }
}
