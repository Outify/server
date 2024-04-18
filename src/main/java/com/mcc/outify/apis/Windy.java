package com.mcc.outify.apis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class Windy {

    @Value("${windy-key}")
    private String windyKey;

    public void run() throws Exception {

        String requestBody =
                "{\"lat\": 49.809," +
                        "\"lon\": 16.787," +
                        "\"model\": \"gfs\"," +
                        "\"parameters\": [\"temp\", \"dewpoint\", \"precip\", \"convPrecip\", \"snowPrecip\"," +
                        "\"wind\", \"windGust\", \"cape\", \"ptype\", \"lclouds\", \"mclouds\", \"hclouds\", " +
                        "\"rh\", \"gh\", \"pressure\"]," +
                        "\"levels\": [\"surface\"]," +
                        "\"key\":\"" + windyKey + "\"}";

        StringBuilder urlBuilder = new StringBuilder("https://api.windy.com/api/point-forecast/v2");
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
        System.out.println(sb);
    }
}
