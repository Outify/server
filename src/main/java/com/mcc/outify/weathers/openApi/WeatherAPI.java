package com.mcc.outify.weathers.openApi;

import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.entity.WeatherDataEntity;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface WeatherAPI {

    List<WeatherDataEntity> getWeatherData(LocationEntity location) throws IOException, ParseException;

}
