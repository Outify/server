package com.mcc.outify.weathers;

import com.mcc.outify.weathers.openApi.OpenMeteoAPI;
import com.mcc.outify.weathers.openApi.YrAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherDataRunner implements ApplicationRunner {

    private final OpenMeteoAPI openMeteo;
    private final YrAPI yr;
    private final LocationList locationList;

    @Override
    public void run(ApplicationArguments args) {
        locationList.readExcel();
        excuteWeatherOpenAPIs();
    }

    @Scheduled(cron = "0 5 0 * * ?")
    public void weatherAPIScheduled() {
        excuteWeatherOpenAPIs();
    }

    private void excuteWeatherOpenAPIs() {
        getYrData();
//        getOpenMeteoData();
    }

    private void getOpenMeteoData() {
        try {
            openMeteo.getWeatherData();
        } catch (Exception e) {
            System.err.println("An error occurred during weather data execution: " + e.getMessage());
        }
    }

    private void getYrData() {
        try {
            yr.getWeatherData();
        } catch (Exception e) {
            System.err.println("An error occurred during weather data execution: " + e.getMessage());
        }
    }

}