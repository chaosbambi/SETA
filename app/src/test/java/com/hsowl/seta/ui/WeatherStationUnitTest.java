package com.hsowl.seta.ui;

import com.hsowl.seta.data.WeatherStation;
import com.hsowl.seta.data.weatherData.WeatherData;

import junit.framework.Assert;

import org.junit.Test;

public class
WeatherStationUnitTest {

    @Test
    public void testApiCall(){

        WeatherStation weatherStation = new WeatherStation(52.016859, 8.904493);

        WeatherData weatherData = weatherStation.getWeatherData();

        Assert.assertNotNull(weatherData);
    }
}
