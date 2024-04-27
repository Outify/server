package com.mcc.outify.weathers;

import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import com.mcc.outify.weathers.openApi.OpenMeteoAPI;
import com.mcc.outify.weathers.openApi.YrAPI;
import com.mcc.outify.weathers.repository.WeatherDataRepository;
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
    private final WeatherDataRepository weatherDataRepository;

    @Override
    public void run(ApplicationArguments args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::executeAfterDelay, 10, TimeUnit.SECONDS);
    }

    private void executeAfterDelay() {
        locationList.readExcel();
        saveSource();
        executeWeatherOpenAPIs();
    }

    @Scheduled(cron = "0 5 0 * * ?")
    public void weatherAPIScheduled() {
        executeWeatherOpenAPIs();
    }

    private void saveSource() {
        WeatherSourceEntity meteoSource = weatherSourceRepository.findBySource(WeatherSourceEntity.WeatherSource.METEO);
        if (meteoSource == null) {
            meteoSource = new WeatherSourceEntity(WeatherSourceEntity.WeatherSource.METEO);
            weatherSourceRepository.save(meteoSource);
        }

        WeatherSourceEntity yrSource = weatherSourceRepository.findBySource(WeatherSourceEntity.WeatherSource.YR);
        if (yrSource == null) {
            yrSource = new WeatherSourceEntity(WeatherSourceEntity.WeatherSource.YR);
            weatherSourceRepository.save(yrSource);
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