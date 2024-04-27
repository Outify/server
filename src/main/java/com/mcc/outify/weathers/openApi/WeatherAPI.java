package com.mcc.outify.weathers.openApi;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface WeatherAPI {

    void getWeatherData() throws IOException, ParseException;

}
