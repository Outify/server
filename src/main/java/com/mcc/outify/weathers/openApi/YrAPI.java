package com.mcc.outify.weathers.openApi;

import com.mcc.outify.weathers.WeatherPolicy;
import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.entity.WeatherDataEntity;
import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import com.mcc.outify.weathers.repository.LocationRepository;
import com.mcc.outify.weathers.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
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
public class YrAPI implements WeatherAPI {

    private final LocationRepository locationRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherPolicy weatherPolicy;

    @Value("${yr-user-agent}")
    private String yrUserAgent;

    @Transactional
    @Override
    public void getWeatherData() throws IOException, ParseException {

        WeatherSourceEntity weatherSource = weatherPolicy.isExistSourceCheck("YR");
        List<LocationEntity> locationList = locationRepository.findAll();

        for (LocationEntity location : locationList) {
            weatherDataRepository.deleteAllByLocation(location);

            String latitude = location.getLatitude();
            String longitude = location.getLongitude();

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

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(sb.toString());
            JSONObject parse_properties = (JSONObject) obj.get("properties");
            JSONArray parse_timeseries = (JSONArray) parse_properties.get("timeseries");
            for (int i = 0; i < parse_timeseries.size() - 1; i++) {
                JSONObject parsed_obj = (JSONObject) parse_timeseries.get(i);
                String parsedTime = (String) parsed_obj.get("time");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("Asia/Seoul"));
                LocalDateTime time = LocalDateTime.parse(parsedTime, formatter);

                JSONObject parse_data = (JSONObject) parsed_obj.get("data");
                JSONObject parse_instant = (JSONObject) parse_data.get("instant");
                JSONObject parse_details = (JSONObject) parse_instant.get("details");
                Double tmp = ((Number) parse_details.get("air_temperature")).doubleValue();
                Double wsd = ((Number) parse_details.get("wind_speed")).doubleValue();
                Double wgu = ((Number) parse_details.get("wind_speed_of_gust")).doubleValue();
                Double hum = ((Number) parse_details.get("relative_humidity")).doubleValue();
                Double dpt = ((Number) parse_details.get("dew_point_temperature")).doubleValue();

                JSONObject parse_nextHours = (JSONObject) parsed_obj.get("next_1_hours");
                JSONObject parse_summary = (JSONObject) parse_nextHours.get("summary");
                String sky = (String) parse_summary.get("symbol_code");

                JSONObject parse_hourDetails = (JSONObject) parse_nextHours.get("details");
                Double pcp = ((Number) parse_hourDetails.get("precipitation_amount")).doubleValue();

                WeatherDataEntity weatherData = new WeatherDataEntity(location, weatherSource, time, sky, tmp, pcp, wsd, wgu, hum, dpt);
                weatherDataRepository.save(weatherData);
            }
        }
    }
}