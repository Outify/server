package com.mcc.outify.weathers.service;

import com.mcc.outify.weathers.dto.WeatherResponseDto;
import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.entity.WeatherDataEntity;
import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import com.mcc.outify.weathers.repository.LocationRepository;
import com.mcc.outify.weathers.repository.WeatherDataRepository;
import com.mcc.outify.weathers.repository.WeatherSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final LocationRepository locationRepository;

    private final WeatherDataRepository weatherDataRepository;

    private final WeatherSourceRepository weatherSourceRepository;

    public Map<String, Object> getLocationWeather(String addr) {
        Map<String, Object> resData = new LinkedHashMap<>();

        StringTokenizer st = new StringTokenizer(addr);
        String highAddr = st.nextToken();
        String midAddr = st.nextToken();
        String lowAddr = st.nextToken();

        LocationEntity location = findLocation(highAddr, midAddr, lowAddr);
        resData.put("location", location);

        LocalDateTime now = LocalDateTime.now();
        List<WeatherSourceEntity> weatherSourceList = weatherSourceRepository.findAll();
        for (WeatherSourceEntity weatherSource : weatherSourceList) {
            List<WeatherDataEntity> weatherDataList = weatherDataRepository.findAllByLocationAndWeatherSource(location, weatherSource);
            List<WeatherResponseDto> validWeatherDataList = new ArrayList<>();

            for (int i = 0; i < weatherDataList.size(); i++) {
                WeatherDataEntity weatherData = weatherDataList.get(i);
                LocalDateTime time = weatherData.getTime();

                // 현재 시간 이후의 데이터만 필터링
                if (!time.isBefore(now)) {
                    validWeatherDataList.add(new WeatherResponseDto(weatherData, time));

                    // 6시간 간격 데이터를 3시간 간격으로 채우기
                    if (i < weatherDataList.size() - 1) {
                        WeatherDataEntity nextWeatherData = weatherDataList.get(i + 1);
                        LocalDateTime nextTime = nextWeatherData.getTime();

                        while (time.plusHours(3).isBefore(nextTime)) {
                            time = time.plusHours(3);
                            validWeatherDataList.add(new WeatherResponseDto(nextWeatherData, time));
                        }
                    }
                }
            }
            resData.put(weatherSource.getSource().name(), validWeatherDataList);
        }
        return resData;
    }

    private LocationEntity findLocation(String highAddr, String midAddr, String lowAddr) {

        LocationEntity location = locationRepository.findByHighAddrAndMidAddrAndLowAddr(highAddr, midAddr, lowAddr);
        if (location == null) {
            location = locationRepository.findByHighAddrAndMidAddrAndLowAddr(highAddr, midAddr, "");
            if (location == null) {
                location = locationRepository.findByHighAddrAndMidAddrAndLowAddr(highAddr, "", "");
                if (location == null) {
                    throw new IllegalArgumentException("위치 및 주소 정보를 확인할 수 없습니다.");
                }
            }
        }
        return location;
    }

}
