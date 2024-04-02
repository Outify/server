package com.mcc.outify.weatherData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class Windy implements ApplicationRunner {

    @Value("${windy-key}")
    private String windyKey;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String requestBody =
                "{\"lat\": 49.809," +
                "\"lon\": 16.787," +
                "\"model\": \"gfs\"," +
                "\"parameters\": [\"temp\", \"precip\", \"wind\", \"windGust\"]," +
                "\"levels\": [\"surface\"]," +
                "\"key\":\"" + windyKey + "\"}";

        StringBuilder urlBuilder = new StringBuilder("https://api.windy.com/api/point-forecast/v2"); /*URL*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}
