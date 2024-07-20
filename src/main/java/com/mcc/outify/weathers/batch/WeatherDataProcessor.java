package com.mcc.outify.weathers.batch;

import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.entity.WeatherDataEntity;
import com.mcc.outify.weathers.openApi.WeatherAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WeatherDataProcessor implements ItemProcessor<LocationEntity, List<WeatherDataEntity>> {

    private final List<WeatherAPI> weatherAPIs;

    @Override
    public List<WeatherDataEntity> process(LocationEntity location) throws Exception {
        List<WeatherDataEntity> allWeatherDataList = new ArrayList<>();

        for (WeatherAPI weatherAPI : weatherAPIs) {
            List<WeatherDataEntity> weatherDataList = weatherAPI.getWeatherData(location);
            allWeatherDataList.addAll(weatherDataList);
        }
        return allWeatherDataList;
    }

}
