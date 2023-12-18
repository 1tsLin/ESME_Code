package com.esme.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Score {
    private int id;
    private String playerName;
    private int score;
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getPlayerName() {
        return playerName;
    }
    public int getScore() {
        return score;
    }
    public void sendToAPI() {
        // Créer une instance de HttpRequestBuilder
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();

        // Construire la requête POST avec l'URL de l'API
        Net.HttpRequest httpRequest = requestBuilder.newRequest()
                .method(Net.HttpMethods.POST)
                .url("https://api.munier.me/A2MSI3groupe3/Score")
                .build();

        // Ajouter les données du score dans le corps de la requête au format JSON
        String jsonScore = toJson();
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setContent(jsonScore);

        // Définir le gestionnaire de réponse
        Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                // Traitement de la réponse de l'API après l'envoi des données
                String responseString = httpResponse.getResultAsString();
                JsonValue jsonResponse = new Json().fromJson(JsonValue.class, responseString);

                // Vous pouvez traiter la réponse de l'API ici
                Gdx.app.log("API Response", jsonResponse.toString());
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

        // Envoyer la requête à l'API
        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
    }

    private String toJson() {
        // Convertir l'objet Score en une chaîne JSON
        Json json = new Json();
        return json.toJson(this);
    }
}