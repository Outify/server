package com.mcc.outify.weathers;

import java.io.IOException;

public interface WeatherAPI {

    void getWeatherData(String latitude, String longitude) throws IOException;

}
