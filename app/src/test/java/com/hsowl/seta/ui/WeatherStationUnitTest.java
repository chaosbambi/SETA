package com.hsowl.seta.ui;

import com.hsowl.seta.data.WeatherStation;
import com.hsowl.seta.data.weatherData.City;
import com.hsowl.seta.data.weatherData.WeatherData;

public class
WeatherStationUnitTest {

    public void testApiCall(){
        //Deprecated if using ZIP-Code based request
        /*
        WeatherStation weatherStation = new WeatherStation(52.016859, 8.904493);

        WeatherData weatherData = weatherStation.getCurrentWeatherData();

        Assert.assertNotNull(weatherData);
        */
    }

    public void textToCopyInMainAcivityForTest(){

        WeatherStation ws = new WeatherStation();

        ws.setZip((short) 32657);

        WeatherData wd = ws.getCurrentWeatherData();

        City test = wd.getCity();
    }
}
