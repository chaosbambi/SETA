package com.hsowl.seta.data;

import com.hsowl.seta.data.weatherData.WeatherData;

public class WeatherStation {

    private double lat;

    private double lon;

    private String weatherApiKey;

    private final String baseURL = "http://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}";

    public WeatherStation(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.weatherApiKey = SensitiveData.OPEN_WEATHER_API_KEY;
    }

    public WeatherData getWeatherData(){
        String response = null;
        String url = baseURL;
        WeatherData weatherData = null;


        url = url.replace("{lat}",lat+"");
        url = url.replace("{lon}",lon+"");
        url = url.concat("&APPID=" + weatherApiKey);

        new GetWeatherTask().execute(url,null, response);

        //TODO: Fill weatherData object with Response through JSON parser

        return weatherData;
    }
}
