package com.esme.game.states;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.esme.game.managers.GameStateManager;
import com.esme.game.utils.Constants;

public class MainMenu extends GameState {

    private Texture background, ground, character;
    private BitmapFont gameTitleText, touchText, toText, startText;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private GlyphLayout gameTitleGlyph, touchGlyph, toGlyph, startGlyph;

    public MainMenu(GameStateManager gsm) {
        super(gsm);
        this.background = new Texture(Gdx.files.internal("bg.jpg")); //image à changer
        this.ground = new Texture(Gdx.files.internal("bg_ground.png")); //image à changer
        this.character = new Texture(Gdx.files.internal("data/character/basechara.png")); //image à changer

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fs-gravity.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        this.parameter.size = 128; //tout texte généré après cette ligne a une taille de 128
        this.gameTitleText = this.generator.generateFont(this.parameter);

        this.parameter.size = 64;
        this.touchText = this.generator.generateFont(this.parameter);
        this.toText = this.generator.generateFont(this.parameter);
        this.startText = this.generator.generateFont(this.parameter);

        this.gameTitleGlyph = new GlyphLayout(); //récupère la taille du texte
        this.touchGlyph = new GlyphLayout();
        this.startGlyph = new GlyphLayout();
        this.toGlyph = new GlyphLayout();

        this.gameTitleGlyph.setText(this.gameTitleText, Constants.GAME_TITLE); //Set les textes à dessiner
        this.touchGlyph.setText(this.touchText, "Touch");
        this.toGlyph.setText(this.toText, "to");
        this.startGlyph.setText(this.startText, "start");

        Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("data/bg_sound.ogg"));
        menuMusic.setLooping(true);
        menuMusic.play();

        this.cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT); //centrer la position sur l'écran
        //(y augmente sur la hauteur si la souris monte - false, taille champ de vision)
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){ //Si l'écran est touché
            this.gsm.set(new PlayState(this.gsm));
        }
    }

    @Override
    public void update(float dt) {
        this.handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        //Mise en place d'une matrice de projection, place les objets par rapport à la position de la caméra
        sb.setProjectionMatrix(this.cam.combined);
        sb.begin();
        sb.draw(this.background,0,0);
        sb.draw(this.ground,0,0);
        sb.draw(this.character,this.cam.viewportWidth/2-this.character.getWidth()/2,this.ground.getHeight());
        //on dessine le texte à partir du sb
        this.gameTitleText.draw(sb, this.gameTitleGlyph, this.cam.viewportWidth/2-this.gameTitleGlyph.width/2, this.cam.viewportHeight-this.gameTitleGlyph.height);
        this.touchText.draw(sb,this.touchGlyph, this.cam.viewportWidth/2-this.touchGlyph.width/2, this.cam.viewportHeight/2);
        this.toText.draw(sb,this.toGlyph, this.cam.viewportWidth/2-this.toGlyph.width/2, this.cam.viewportHeight/2- this.touchGlyph.height);
        this.startText.draw(sb,this.startGlyph, this.cam.viewportWidth/2-this.startGlyph.width/2, this.cam.viewportHeight/2- this.touchGlyph.height*2);

        sb.end();
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.ground.dispose();
        this.character.dispose();
        this.gameTitleText.dispose();
        this.touchText.dispose();
        this.toText.dispose();
        this.startText.dispose();
        this.generator.dispose();
    }
}
