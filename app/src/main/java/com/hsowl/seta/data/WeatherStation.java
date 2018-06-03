package com.hsowl.seta.data;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsowl.seta.data.weatherData.WeatherData;

import java.util.TreeMap;

public class WeatherStation {

    private TreeMap<Long,WeatherData> weatherHistory;

    private double lat;

    private double lon;

    private String weatherApiKey;

    private final String baseURL = "http://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}";

    private final long weatherUpdateDowntime = 30000;

    public WeatherStation(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.weatherApiKey = SensitiveData.OPEN_WEATHER_API_KEY;
    }


    public TreeMap<Long, WeatherData> getWeatherHistory() {
        return weatherHistory;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    //TODO: Test method working with weatherData history
    public WeatherData getCurrentWeatherData(){

        WeatherData wd = null;

        if (weatherHistory.lastKey() + weatherUpdateDowntime > System.currentTimeMillis()){

            wd = weatherHistory.lastEntry().getValue();

        }else{
            String response = null;
            String url = baseURL;

            url = url.replace("{lat}",lat+"");
            url = url.replace("{lon}",lon+"");
            url = url.concat("&APPID=" + weatherApiKey);


            JSONWeatherTask task = new JSONWeatherTask();
            try {

                wd = task.execute(url).get();
                weatherHistory.put(System.currentTimeMillis(),wd);

            }catch (Exception e){
                Log.getStackTraceString(e);
            }
        }

        return wd;
    }

    private static class JSONWeatherTask extends AsyncTask<String, Void, WeatherData>{

        @Override
        protected WeatherData doInBackground(String... strings) {
            WeatherData wd = null;
            String data = ((new HttpClient().getData(strings[0])));

            //Parse the JSON response in a class of the WheaterData type
            try {
                Gson gson = new GsonBuilder().create();

                wd = gson.fromJson(data, WeatherData.class);
            }catch (Exception e){
                Log.getStackTraceString(e);
            }

            return wd;
        }
    }
}
