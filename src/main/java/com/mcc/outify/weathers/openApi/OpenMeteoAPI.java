package com.mcc.outify.weathers.openApi;

import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.entity.WeatherDataEntity;
import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import com.mcc.outify.weathers.repository.LocationRepository;
import com.mcc.outify.weathers.repository.WeatherDataRepository;
import com.mcc.outify.weathers.repository.WeatherSourceRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OpenMeteoAPI implements WeatherAPI {
    private final LocationRepository locationRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherSourceRepository weatherSourceRepository;

    String option = "temperature_2m,relative_humidity_2m,dew_point_2m,precipitation,rain,weather_code,cloud_cover,wind_speed_10m,wind_gusts_10m";
    String timezone = "Asia/Seoul";
    String forecastDays = "14";

    @Transactional
    @Override
    public void getWeatherData() throws IOException, ParseException {

        WeatherSourceEntity weatherSource = weatherSourceRepository.findBySource(WeatherSourceEntity.WeatherSource.METEO);
        List<LocationEntity> locationList = locationRepository.findAll();

        for (LocationEntity location : locationList) {

            weatherDataRepository.deleteByLocationAndWeatherSource(location, weatherSource);

            String latitude = location.getLatitude();
            String longitude = location.getLongitude();

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

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(sb.toString());
            JSONObject parse_hourly = (JSONObject) obj.get("hourly");

            JSONArray parse_time = (JSONArray) parse_hourly.get("time");
            JSONArray temperature_2m = (JSONArray) parse_hourly.get("temperature_2m");
            JSONArray relative_humidity_2m = (JSONArray) parse_hourly.get("relative_humidity_2m");
            JSONArray dew_point_2m = (JSONArray) parse_hourly.get("dew_point_2m");
            JSONArray precipitation = (JSONArray) parse_hourly.get("precipitation");
            JSONArray weather_code = (JSONArray) parse_hourly.get("weather_code");
            JSONArray cloud_cover = (JSONArray) parse_hourly.get("cloud_cover");
            JSONArray wind_speed_10m = (JSONArray) parse_hourly.get("wind_speed_10m");
            JSONArray wind_gusts_10m = (JSONArray) parse_hourly.get("wind_gusts_10m");

            for (int t = 0; t < parse_time.size(); t++) {
                String parsedTime = (String) parse_time.get(t);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Seoul"));
                LocalDateTime time = LocalDateTime.parse(parsedTime, formatter);
                if (t % 3 == 0) {
                    Long temp_sky = ((Number) weather_code.get(t)).longValue();
                    String sky = String.valueOf(temp_sky);
                    Double tmp = ((Number) temperature_2m.get(t)).doubleValue();
                    Double pcp = ((Number) precipitation.get(t)).doubleValue();
                    Double wsd = ((Number) wind_speed_10m.get(t)).doubleValue();
                    Double wgu = ((Number) wind_gusts_10m.get(t)).doubleValue();
                    Long temp_hum = ((Number) relative_humidity_2m.get(t)).longValue();
                    Double hum = Double.valueOf(temp_hum);
                    Double dpt = ((Number) dew_point_2m.get(t)).doubleValue();

                    WeatherDataEntity weatherData = new WeatherDataEntity(location, weatherSource, time, sky, tmp, pcp, wsd, wgu, hum, dpt);
                    weatherDataRepository.save(weatherData);
                }
            }
        }
    }

}