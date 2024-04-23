package com.mcc.outify.weathers;

import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import com.mcc.outify.weathers.repository.WeatherSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeatherPolicy {

    private final WeatherSourceRepository weatherSourceRepository;

    public WeatherSourceEntity isExistSourceCheck(String sourceName) {
        WeatherSourceEntity result = null;
        try {
            WeatherSourceEntity.WeatherSource sourceEnum = WeatherSourceEntity.WeatherSource.valueOf(sourceName.toUpperCase());

            WeatherSourceEntity existingSource = weatherSourceRepository.findBySource(sourceEnum);

            if (existingSource == null) {
                WeatherSourceEntity.WeatherSource source = sourceEnum;
                WeatherSourceEntity newSource = new WeatherSourceEntity(source);
                weatherSourceRepository.save(newSource);
                result = newSource;
            } else {
                return result = existingSource;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid source: " + sourceName);
        }
        return result;
    }

}