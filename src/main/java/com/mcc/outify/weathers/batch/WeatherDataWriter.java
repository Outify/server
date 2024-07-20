package com.mcc.outify.weathers.batch;

import com.mcc.outify.weathers.entity.WeatherDataEntity;
import com.mcc.outify.weathers.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WeatherDataWriter implements ItemWriter<List<WeatherDataEntity>> {

    private final WeatherDataRepository weatherDataRepository;

    @Override
    public void write(Chunk<? extends List<WeatherDataEntity>> weatherDataEntities) throws Exception {
        List<WeatherDataEntity> weatherDataEntityList = new ArrayList<>();
        for (List<WeatherDataEntity> weatherDataList : weatherDataEntities) {
            weatherDataEntityList.addAll(weatherDataList);
        }
        weatherDataRepository.saveAll(weatherDataEntityList);
    }
}
