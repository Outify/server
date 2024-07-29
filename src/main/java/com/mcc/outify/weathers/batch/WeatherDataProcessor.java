package com.mcc.outify.weathers.batch;

import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.entity.WeatherDataEntity;
import com.mcc.outify.weathers.openApi.WeatherAPI;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WeatherDataProcessor implements ItemProcessor<LocationEntity, List<WeatherDataEntity>> {

    private final List<WeatherAPI> weatherAPIs;

    @Override
    public List<WeatherDataEntity> process(LocationEntity location) throws Exception {

        try {
            List<WeatherDataEntity> weatherDataList = getWeatherDataWithRetry(location);
            return weatherDataList;
        } catch (Exception e) {
            System.err.println("Exception for location: " + location);
            throw e;
        }
    }

    public List<WeatherDataEntity> getWeatherDataWithRetry(LocationEntity location) throws IOException, ParseException {
        int retryCount = 3;
        List<WeatherDataEntity> allWeatherDataList = new ArrayList<>();

        while (retryCount > 0) {
            try {
                for (WeatherAPI weatherAPI : weatherAPIs) {
                    List<WeatherDataEntity> weatherDataList = weatherAPI.getWeatherData(location);
                    allWeatherDataList.addAll(weatherDataList);
                }
                return allWeatherDataList;
            } catch (Exception e) {
                System.err.println("ParseException for location: " + location + ", error: " + e.getMessage());
                throw e;
            }
        }
        return allWeatherDataList;
    }

}
