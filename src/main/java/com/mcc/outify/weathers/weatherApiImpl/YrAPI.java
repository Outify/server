package com.mcc.outify.weathers.weatherApiImpl;

import com.mcc.outify.weathers.WeatherAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class YrAPI implements WeatherAPI {

    @Value("${yr-user-agent}")
    private String yrUserAgent;

    @Override
    public void getWeatherData(String latitude, String longitude) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://api.met.no/weatherapi/locationforecast/2.0/complete");
        urlBuilder.append("?" + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("User-Agent", yrUserAgent);
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

        // 아래 print문 삭제 후, DB 저장 로직 구현 필요
        System.out.println(sb);
    }
}
