package com.mcc.outify.weathers;

import com.mcc.outify.weathers.weatherApiImpl.OpenMeteoAPI;
import com.mcc.outify.weathers.weatherApiImpl.YrAPI;
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

    // locationList 꺼내는 메서드 구현 필요
    // 1. 위치 정보가 담긴 외부 문서 scan하고, locationList 변수 선언 및 할당

    @Override
    public void run(ApplicationArguments args) {
        // 2. for (LocationEntity location : locationList) 형태로 각각의 location 특정
        // 3. location 내부의 위도, 경도 등 데이터 꺼내고, 변수 선언하여 할당
        // 4. 할당된 변수는 아래의 메서드 중 필요한 부분에 매개변수로 제공
        executeOpenMeteo("52.52", "13.41");
        executeYr("52.52", "13.41");
    }

    @Scheduled(cron = "0 35 0 * * ?")
    public void weatherAPIScheduled() {
        // 2. for (LocationEntity location : locationList) 형태로 각각의 location 특정
        // 3. location 내부의 위도, 경도 등 데이터 꺼내고, 변수 선언하여 할당
        // 4. 할당된 변수는 아래의 메서드 중 필요한 부분에 매개변수로 제공
        executeOpenMeteo("52.52", "13.41");
        executeYr("52.52", "13.41");
    }

    private void executeOpenMeteo(String latitude, String longitude) {
        try {
            openMeteo.getWeatherData(latitude, longitude);
        } catch (Exception e) {
            System.err.println("An error occurred during weather data execution: " + e.getMessage());
        }
    }

    private void executeYr(String latitude, String longitude) {
        try {
            // 아래 메서드에 위도, 경도 삽입해주는 반복문 로직 작성 필요
            // 1. locationList를 외부 문서로부터 scan
            // 2. for (LocationEntity location : locationList) 형태로 각각의 location 특정
            // 3. location 내부의 위도, 경도 등 데이터 꺼내고, 변수 선언하여 할당
            // 4. 아래의 메서드에 필요한 매개변수 할당
            yr.getWeatherData(latitude, longitude);
        } catch (Exception e) {
            System.err.println("An error occurred during weather data execution: " + e.getMessage());
        }
    }

}