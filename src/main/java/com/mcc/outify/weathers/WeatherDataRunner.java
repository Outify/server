package com.mcc.outify.weathers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherDataRunner implements ApplicationRunner {

    private final Yr yr;
    private final Windy windy;
    private final OpenMeteo openMeteo;

    @Override
    public void run(ApplicationArguments args) {
        try {
            windy.run();
            yr.run();
            openMeteo.run();
        } catch (Exception e) {
            System.err.println("An error occurred during weather data execution: " + e.getMessage());
        }
    }

    @Scheduled(cron = "0 5 0 * * ?") // 초 분 시 일 월 요일
    public void windyAndMeteoScheduled() {
        executeWindyAndMeteo();
    }

    @Scheduled(cron = "0 35 23 * * ?") // 초 분 시 일 월 요일
    public void yrScheduled() {
        executeYr();
    }

    private void executeWindyAndMeteo() {
        try {
            windy.run();
            openMeteo.run();
        } catch (Exception e) {
            System.err.println("An error occurred during weather data execution: " + e.getMessage());
        }
    }

    private void executeYr() {
        try {
            yr.run();
        } catch (Exception e) {
            System.err.println("An error occurred during weather data execution: " + e.getMessage());
        }
    }
}