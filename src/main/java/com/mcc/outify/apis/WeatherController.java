package com.mcc.outify.apis;

import com.mcc.outify.weathers.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weathers")
    public Map<String, Object> getLocationWeather(@RequestParam String addr) {
        Map<String, Object> resData = weatherService.getLocationWeather(addr);

        return resData;
    }

}
