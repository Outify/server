package com.mcc.outify.weathers;

import com.mcc.outify.weathers.entity.WeatherDataEntity;
import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weathers")
    public Map<WeatherSourceEntity.WeatherSource, List<WeatherDataEntity>> getLocationWeather(@RequestBody String addr) {
        Map<WeatherSourceEntity.WeatherSource, List<WeatherDataEntity>> resData = weatherService.getLocationWeather(addr);

        return resData;
    }

}
