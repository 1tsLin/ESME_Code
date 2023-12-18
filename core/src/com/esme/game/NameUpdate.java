package com.esme.game;

import com.badlogic.gdx.Input;

public class NameUpdate implements Input.TextInputListener {
    @Override
    public void input (String text) {
        System.out.println("Player entered name: " + text);
        Score scoreToSend = new Score();
        scoreToSend.setPlayerName(text);
        scoreToSend.setScore(100);
        scoreToSend.sendToAPI();
    }

    @Override
    public void canceled () {
    }
}