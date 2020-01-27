package com.rybalko.ua.lorstats.http;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpResponseBuilder {

    private HttpURLConnection connection;

    public HttpResponseBuilder(HttpURLConnection connection) {
        this.connection = connection;
    }

    public JSONObject response() throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        int responseCode = connection.getResponseCode();
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        myResponse.put("ResponseCode", responseCode);
        return myResponse;
    }
}
