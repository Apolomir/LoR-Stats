package com.rybalko.ua.lorstats.app;

import com.rybalko.ua.lorstats.exel.ExcelDocumentBuilder;
import com.rybalko.ua.lorstats.http.HttpResponseBuilder;
import com.rybalko.ua.lorstats.view.ViewBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ResponseHandler {

    private ResponseRunnable responseRunnable;
    private ScheduledExecutorService thread;

    public ResponseHandler(ViewBuilder viewBuilder) {
        this.thread = Executors.newSingleThreadScheduledExecutor();
        this.responseRunnable = new ResponseRunnable(viewBuilder);
    }

    public void execute() {
        thread.scheduleAtFixedRate(responseRunnable, 0, 10, TimeUnit.SECONDS);
    }

    private static class ResponseRunnable implements Runnable {

        private ViewBuilder view;
        private ExcelDocumentBuilder excel;
        private int prevGameId;

        private ResponseRunnable(ViewBuilder viewBuilder) {
            this.view = viewBuilder;
            this.prevGameId = -1;
            this.excel = new ExcelDocumentBuilder();
        }

        @Override
        public void run() {
            try {
                String url = "http://localhost:21337/game-result";
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                HttpResponseBuilder responseBuilder = new HttpResponseBuilder(connection);
                JSONObject myResponse = responseBuilder.response();
                int gameId = myResponse.getInt("GameID");
                boolean gameStatus = myResponse.getBoolean("LocalPlayerWon");
                editView(gameId, gameStatus);
                excel.buildDocument(myResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void editView(int gameId, boolean gameStatus) {
            if(gameId > -1 && gameId != prevGameId) {
                if (gameStatus) {
                    view.setWins();
                } else {
                    view.setLoses();
                }
                prevGameId = gameId;
            }
        }
    }
}
