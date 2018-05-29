package com.hsowl.seta.ui;

import com.hsowl.seta.data.WeatherStation;
import com.hsowl.seta.data.weatherData.WeatherData;

public class WeatherStationUnitTest {
    public static int testApiCall(){
        int errorcode = 0;

        WeatherStation weatherStation = new WeatherStation(52.016859, 8.904493);

        WeatherData weatherData = weatherStation.getWeatherData();

        if (weatherData == null) {
            errorcode = 1;
        }

        return errorcode;
    }
}
