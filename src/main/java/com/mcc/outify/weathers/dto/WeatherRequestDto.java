package com.mcc.outify.weathers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WeatherRequestDto {

    @NotBlank
    private String addr;

}
