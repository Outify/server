package com.mcc.outify.weathers;

import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.entity.WeatherDataEntity;
import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import com.mcc.outify.weathers.repository.LocationRepository;
import com.mcc.outify.weathers.repository.WeatherDataRepository;
import com.mcc.outify.weathers.repository.WeatherSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final LocationRepository locationRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherSourceRepository weatherSourceRepository;

    public Map<WeatherSourceEntity.WeatherSource, List<WeatherDataEntity>> getLocationWeather(String addr) {
        Map<WeatherSourceEntity.WeatherSource, List<WeatherDataEntity>> weatherDatas = new HashMap<>();

        String[] addrArr = addr.split(" ");
        String highAddr = addrArr[0];
        String midAddr = addrArr[1];
        String lowAddr = addrArr[2];

        LocationEntity location = locationRepository.findByHighAddrAndMidAddrAndLowAddr(highAddr, midAddr, lowAddr);
        if (location == null) {
            location = locationRepository.findByHighAddrAndMidAddr(highAddr, midAddr);
            if (location == null) {
                location = locationRepository.findByHighAddr(highAddr);
                if(location == null) {
                    throw new IllegalArgumentException("위치 및 주소 정보를 확인할 수 없습니다.");
                }
            }
        }

        List<WeatherSourceEntity> weatherSourceList = weatherSourceRepository.findAll();
        for (WeatherSourceEntity weatherSource : weatherSourceList) {
            List<WeatherDataEntity> weatherDataList = weatherDataRepository.findAllByLocationAndWeatherSource(location, weatherSource);

            List<WeatherDataEntity> validWeatherDataList = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();

            for (int i = 0; i < weatherDataList.size() - 1; i++) {
                WeatherDataEntity weatherData = weatherDataList.get(i);
                WeatherDataEntity nextWeatherData = weatherDataList.get(i + 1);
                LocalDateTime time = weatherData.getTime();
                LocalDateTime nextTime = nextWeatherData.getTime();

                if (!time.isBefore(now) && nextTime.minusHours(1).isEqual(time) && time.getHour() % 3 == 0) {
                    validWeatherDataList.add(weatherData);
                } else if (!time.isBefore(now) && nextTime.minusHours(6).isEqual(time)) {
                    for (int j = 5; j >= 0; j--) {
                        LocalDateTime previousTime = time.minusHours(j);
                        if (previousTime.getHour() % 3 == 0) {
                            WeatherDataEntity validWeatherData = new WeatherDataEntity(weatherData, previousTime);
                            validWeatherDataList.add(validWeatherData);
                        }
                    }
                }
            }
            weatherDatas.put(weatherSource.getSource(), validWeatherDataList);
        }
        return weatherDatas;
    }

}
