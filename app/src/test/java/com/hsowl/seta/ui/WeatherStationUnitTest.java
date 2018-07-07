package com.hsowl.seta.ui;

import com.hsowl.seta.data.OwmWeatherStation;
import com.hsowl.seta.data.weatherData.City;
import com.hsowl.seta.data.weatherData.WeatherData;

public class
WeatherStationUnitTest {

    public void testApiCall(){
        //Deprecated if using ZIP-Code based request
        /*
        OwmWeatherStation weatherStation = new OwmWeatherStation(52.016859, 8.904493);

        WeatherData weatherData = weatherStation.getCurrentWeatherData();

        Assert.assertNotNull(weatherData);
        */
    }

    public void textToCopyInMainAcivityForTest(){

        OwmWeatherStation ws = new OwmWeatherStation();

        ws.setZip((short) 32657);

        WeatherData wd = ws.getCurrentWeatherData();

        City test = wd.getCity();
    }
}
