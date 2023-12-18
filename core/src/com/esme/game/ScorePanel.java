package com.esme.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ScorePanel {

    private static final String API_URL = "https://api.munier.me/A2MSI3groupe3/Score";

    private Skin skin;
    private Stage stage;
    private Table scoreTable;

    public ScorePanel() {
        scoreTable = new Table();
        stage.addActor(scoreTable);
        fetchScoresFromAPI();
    }

    private void fetchScoresFromAPI() {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(API_URL).build();

        Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String responseString = httpResponse.getResultAsString();
                Array<Score> scores = parseScores(responseString);
                updateScoreTable(scores);
            }

            @Override
            public void failed(Throwable t) {
                // Gérer les erreurs de connexion
                t.printStackTrace();
            }

            @Override
            public void cancelled() {
                // La requête a été annulée
            }
        };

        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
    }

    private Array<Score> parseScores(String json) {
        Json jsonParser = new Json();
        return jsonParser.fromJson(Array.class, Score.class, json);
    }

    private void updateScoreTable(Array<Score> scores) {
        // Effacer les anciens scores de la table
        scoreTable.clear();

        // Ajouter les nouveaux scores à la table
        for (Score score : scores) {
            Label nameLabel = new Label(score.getPlayerName(), skin);
            Label scoreLabel = new Label(String.valueOf(score.getScore()), skin);

            scoreTable.add(nameLabel).pad(10);
            scoreTable.add(scoreLabel).pad(10);
            scoreTable.row();
        }
    }

    public Stage getStage() {
        return stage;
    }
}
