package com.mcc.outify.weathers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weathers")
    public Map<String, Object> getLocationWeather(@RequestBody WeatherRequestDto weatherRequestDto) {
        String addr = weatherRequestDto.getAddr();
        Map<String, Object> resData = weatherService.getLocationWeather(addr);

        return resData;
    }

}
