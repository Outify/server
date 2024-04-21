package com.mcc.outify.weathers;

import com.mcc.outify.weathers.weatherApiImpl.OpenMeteoAPI;
import com.mcc.outify.weathers.weatherApiImpl.YrAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WeatherDataRunner implements ApplicationRunner {

    private final OpenMeteoAPI openMeteo;
    private final YrAPI yr;

    @Override
    public void run(ApplicationArguments args) {
        excuteWeatherOpenAPIs();
    }

    @Scheduled(cron = "0 35 0 * * ?")
    public void weatherAPIScheduled() {
        excuteWeatherOpenAPIs();
    }

    private void excuteWeatherOpenAPIs() {
        String filePath = "src/main/resources/data/locations.xlsx";
        List<LocationEntity> locationList = LocationList.readExcel(filePath);

        for (int i = 0; i < locationList.size(); i++) {
            String latitude = locationList.get(i).getLatitude();
            String longitude = locationList.get(i).getLongitude();

            getOpenMeteoData(latitude, longitude);
            getYrData(latitude, longitude);
        }
    }

    private void getOpenMeteoData(String latitude, String longitude) {
        try {
            openMeteo.getWeatherData(latitude, longitude);
        } catch (Exception e) {
            System.err.println("An error occurred during weather data execution: " + e.getMessage());
        }
    }

    private void getYrData(String latitude, String longitude) {
        try {
            yr.getWeatherData(latitude, longitude);
        } catch (Exception e) {
            System.err.println("An error occurred during weather data execution: " + e.getMessage());
        }
    }

}