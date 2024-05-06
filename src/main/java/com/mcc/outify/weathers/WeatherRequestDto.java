package com.mcc.outify.weathers;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WeatherRequestDto {

    @NotBlank
    private String addr;

}
