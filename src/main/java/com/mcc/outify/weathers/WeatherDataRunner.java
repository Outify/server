package com.mcc.outify.weathers;

import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import com.mcc.outify.weathers.openApi.OpenMeteoAPI;
import com.mcc.outify.weathers.openApi.YrAPI;
import com.mcc.outify.weathers.repository.WeatherSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class WeatherDataRunner implements ApplicationRunner {

    private final OpenMeteoAPI openMeteo;
    private final YrAPI yr;
    private final LocationList locationList;
    private final WeatherSourceRepository weatherSourceRepository;

    @Override
    public void run(ApplicationArguments args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::executeAfterDelay, 10, TimeUnit.SECONDS);
    }

    private void executeAfterDelay() {
//        locationList.readExcel();
//        saveSources();
//        executeWeatherOpenAPIs();
    }

    @Scheduled(cron = "0 5 0 * * ?")
    public void weatherAPIScheduled() {
//        executeWeatherOpenAPIs();
    }

    private void saveSources() {
        saveSource(WeatherSourceEntity.WeatherSource.METEO);
        saveSource(WeatherSourceEntity.WeatherSource.YR);
    }

    private void saveSource(WeatherSourceEntity.WeatherSource sourceType) {
        WeatherSourceEntity weatherSource = weatherSourceRepository.findBySource(sourceType);
        if (weatherSource == null) {
            weatherSource = new WeatherSourceEntity(sourceType);
            weatherSourceRepository.save(weatherSource);
        }
    }

    private void executeWeatherOpenAPIs() {
        getYrData();
        getOpenMeteoData();
    }

    private void getOpenMeteoData() {
        try {
            openMeteo.getWeatherData();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during weather data execution: " + e.getMessage(), e);
        }
    }

    private void getYrData() {
        try {
            yr.getWeatherData();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during weather data execution: " + e.getMessage(), e);
        }
    }

}