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

    private int zip;

    private String weatherApiKey;

    private final long weatherUpdateDowntime = 1800000;

    @Deprecated
    private final String baseCoordURL = "http://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}";

    private final String baseZipURL = "http://api.openweathermap.org/data/2.5/forecast?zip={zip},de";


    /**
     * This constructor creates a new WeatherStation object and initializes the weatherHistory and API-Key Attributes.
     */
    public WeatherStation(){

        this.weatherApiKey = SensitiveData.OPEN_WEATHER_API_KEY;
        this.weatherHistory = new TreeMap<>();
    }

    /**
     *
     * @param zip   A valid german zip code as a short
     * @return  True if the paramter zip code is valid and was set for the Object. Otherwise returns false.
     */
    public boolean setZip(int zip) {
        if (zip >= 1000 && zip <= 99999 ){
            this.zip = zip;
            return true;
        }
        return false;
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

    /**
     * This Method gets a new WeatherData object with an API request, if none was fetched in the last 30 minutes. Otherwise
     * it takes the last fetched WeatherData object. If no WeatherData objects are stored and the API request failed for
     * any reason the method returns null.
     * @return  The latest WeatherData object or null if none is available.
     */
    public WeatherData getCurrentWeatherData(){

        WeatherData wd = null;

        //Check if no past WeatherData objects exist or the last one is outdated.
        if (weatherHistory.size() == 0 ||
                weatherHistory.lastKey() + weatherUpdateDowntime < System.currentTimeMillis()){

            String response = null;
            String url = baseZipURL;

            url = url.replace("{zip}",zip+"");

            /*
            url = url.replace("{lat}", lat+"");
            url = url.replace("{lon}", lon+"");
            */

            url = url.concat("&APPID=" + weatherApiKey);


            JSONWeatherTask task = new JSONWeatherTask();
            try {
                wd = task.execute(url).get();

            }catch (Exception e){
                Log.getStackTraceString(e);
            }

            if (wd != null){

                //Update the latitude and longitude values of the WeatherStation object.
                this.lat = wd.getCity().getCoord().getLat();
                this.lon = wd.getCity().getCoord().getLon();


                //TODO: Add method to control the size of the TreeMap
                //Add the new WeatherData object into the the history
                weatherHistory.put(System.currentTimeMillis(),wd);
            }
        }

        //If no WeatherData object was fetched from the API, insert the last object from the history
        if (wd == null)
            wd = weatherHistory.lastEntry().getValue();

        return wd;
    }

    /**
     * This inner class is used to execute the API request on a different Thread and get the WeatherData object from the JSON response
     */
    private static class JSONWeatherTask extends AsyncTask<String, Void, WeatherData>{

        @Override
        protected WeatherData doInBackground(String... strings) {
            WeatherData wd = null;
            HttpClient httpClient = new HttpClient();
            httpClient.initializeConnection(strings[0]);
            String data = ((new HttpClient().getData()));

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
