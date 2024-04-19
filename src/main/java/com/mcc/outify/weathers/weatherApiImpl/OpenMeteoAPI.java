package com.mcc.outify.weathers.weatherApiImpl;

import com.mcc.outify.weathers.WeatherAPI;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class OpenMeteoAPI implements WeatherAPI {

    @Override
    public void getWeatherData(String latitude, String longitude) throws IOException {

        String option = "temperature_2m,relative_humidity_2m,dew_point_2m,apparent_temperature,precipitation_probability,precipitation,rain,showers,snowfall,snow_depth,weather_code,pressure_msl,surface_pressure,cloud_cover,cloud_cover_low,cloud_cover_mid,cloud_cover_high,visibility,evapotranspiration,et0_fao_evapotranspiration,vapour_pressure_deficit,wind_speed_10m,wind_speed_80m,wind_speed_120m,wind_speed_180m,wind_direction_10m,wind_direction_80m,wind_direction_120m,wind_direction_180m,wind_gusts_10m,temperature_80m,temperature_120m,temperature_180m,soil_temperature_0cm,soil_temperature_6cm,soil_temperature_18cm,soil_temperature_54cm,soil_moisture_0_to_1cm,soil_moisture_1_to_3cm,soil_moisture_3_to_9cm,soil_moisture_9_to_27cm,soil_moisture_27_to_81cm";
        String timezone = "Asia/Seoul";
        String forecastDays = "14";

        StringBuilder urlBuilder = new StringBuilder("https://api.open-meteo.com/v1/forecast");
        urlBuilder.append("?" + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("hourly", "UTF-8") + "=" + URLEncoder.encode(option, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("timezone", "UTF-8") + "=" + URLEncoder.encode(timezone, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("forecast_days", "UTF-8") + "=" + URLEncoder.encode(forecastDays, "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
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

        // 아래 print문 삭제 후, 날짜에 맞는 DB 저장 로직 구현 필요
        System.out.println(sb);
    }
}