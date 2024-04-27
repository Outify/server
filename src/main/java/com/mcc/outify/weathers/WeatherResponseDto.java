package com.mcc.outify.weathers;

import com.mcc.outify.weathers.entity.WeatherDataEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WeatherResponseDto {

    private LocalDateTime time;

    private String sky;

    private Double tmp;

    private Double pcp;

    private Double wsd;

    private Double wgu;

    private Double hum;

    private Double dpt;

    public WeatherResponseDto(WeatherDataEntity weatherData, LocalDateTime time) {
        this.time = time;
        this.sky = weatherData.getSky();
        this.tmp = weatherData.getTmp();
        this.pcp = weatherData.getPcp();
        this.wsd = weatherData.getWsd();
        this.wgu = weatherData.getWgu();
        this. hum = weatherData.getHum();
        this.dpt = weatherData.getDpt();
    }
}
