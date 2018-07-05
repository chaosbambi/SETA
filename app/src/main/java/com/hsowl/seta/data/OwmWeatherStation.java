package com.hsowl.seta.data;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsowl.seta.data.weatherData.WeatherData;
import com.hsowl.seta.logic.PvPrognosis;

import java.util.TreeMap;

public class OwmWeatherStation extends WeatherStation{

    private TreeMap<Long,WeatherData> weatherHistory;

    private String weatherApiKey;

    private final long weatherUpdateDowntime = 1800000;

    private final String baseZipURL = "http://api.openweathermap.org/data/2.5/forecast?zip={zip},de";


    /**
     * This constructor creates a new OwmWeatherStation object and initializes the weatherHistory and
     * API-Key Attributes.
     */
    public OwmWeatherStation(){

        this.weatherApiKey = SensitiveData.OPEN_WEATHER_API_KEY;
        this.weatherHistory = new TreeMap<>();
    }

    /**
     *
     * @param zip   A valid german zip code as a short
     * @return  True if the paramter zip code is valid and was set for the Object. Otherwise returns false.
     */
    @Override
    public boolean setZip(int zip) {
        if (zip >= 1000 && zip <= 99999 ){
            this.zip = zip;
            return true;
        }
        return false;
    }


    /**
     * This Method gets a new WeatherData object with an API request, if none was fetched in the
     * last 30 minutes. Otherwise it takes the last fetched WeatherData object. If no WeatherData
     * objects are stored and the API request failed for any reason the method returns null.
     * @return  The latest WeatherData object or null if none is available.
     */
    @Deprecated
    public WeatherData getCurrentWeatherData(){

        WeatherData wd = null;

        //Check if no past WeatherData objects exist or the last one is outdated.
        if (weatherHistory.size() == 0 ||
                weatherHistory.lastKey() + weatherUpdateDowntime < System.currentTimeMillis()){

            String url = baseZipURL;

            url = url.replace("{zip}",zip+"");

            url = url.concat("&APPID=" + weatherApiKey);


            JSONWeatherTask task = new JSONWeatherTask();
            try {
                wd = task.execute(url).get();

            }catch (Exception e){
                Log.getStackTraceString(e);
            }

            if (wd != null){

                //Update the latitude and longitude values of the OwmWeatherStation object.
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

    @Override
    public void getWeatherFactor(double[] weatherFactor) {
        WeatherData wd = weatherHistory.lastEntry().getValue();
        int remainder = weatherFactor.length % 8;
        for(int i = 0; i < 8 ; i++) {
            for (int j = 0; j < weatherFactor.length / 8; j++) {
                weatherFactor[i * 8 + j] = wd.getList().get(i).getClouds().getAll() / 100.0;
            }
        }
        //fill remaining array fields with last forecast
        for(int i = weatherFactor.length - remainder ; i< i + remainder ; i++){
            weatherFactor[i] = wd.getList().get(8).getClouds().getAll() / 100.0;
        }
    }

    /**
     * Creates a new PvPrognosis object with the latitude and longitude values currently set in
     * this object.
     * @param pvPeakPower   the peak power that is supply by the pv modul
     * @param azimuth       the azimuth of the pv modul
     * @param slope         the slope of the pv modul
     */
    @Override
    public void createPvPrognosis(double pvPeakPower, double azimuth, double slope) {
        this.pvPrognosis = new PvPrognosis(pvPeakPower, lat,lon,azimuth,slope);
    }


    /**
     * This Method checks if the last WeatherData object from the API is not older than 30 minutes.
     * It does not evaluate whether the information from the API has changed in this timespan.
     * @return  true if an update is necessare, false otherwise
     */
    @Override
    public boolean checkForUpdates(){
        //Check if no past WeatherData objects exist or the last one is outdated.
        return weatherHistory.size() == 0 ||
                weatherHistory.lastKey() + weatherUpdateDowntime < System.currentTimeMillis();
    }

    /**
     * This Method sends a request to the OpenWeatherMap API and saves the response into a new
     * WeaterData object which is added internally to the weatherHistory list.
     */
    @Override
    public boolean updateWeatherData(){
        WeatherData wd = null;
        String url = baseZipURL;

        url = url.replace("{zip}",zip+"");
        url = url.concat("&APPID=" + weatherApiKey);

        JSONWeatherTask task = new JSONWeatherTask();
        try {
            wd = task.execute(url).get();

        }catch (Exception e){
            Log.getStackTraceString(e);
        }

        if (wd != null){

            //Add the new WeatherData object into the the history
            weatherHistory.put(System.currentTimeMillis(),wd);

            controlWeatherHistorySize();

            return true;
        }
        return false;
    }

    /**
     * This method can be used to update the latitude and longitude attributes of this object. The
     * values will be taken from the last WeatherData object in the weatherHistory list.
     * @return true if the values were updated succesfully, false otherwise
     */
    @Override
    public boolean updateCoordinates(){
        if (weatherHistory.size()>0){
            WeatherData wd = weatherHistory.lastEntry().getValue();

            this.lat = wd.getCity().getCoord().getLat();
            this.lon = wd.getCity().getCoord().getLon();

            return true;
        }

        return false;
    }

    /**
     * This method returns the last WeatherData object from the weatherHistory list or null if the
     * list is empty.
     * @return  a WeatherData object
     */
    public WeatherData getRecentWeatherData(){
        if (weatherHistory.size() > 0){
            return weatherHistory.lastEntry().getValue();
        }
        return null;
    }

    private void controlWeatherHistorySize(){
        //TODO: Add method to control the size of the TreeMap
    }

    /**
     * This inner class is used to execute the API request on a different Thread and get the
     * WeatherData object from the JSON response
     */
    private static class JSONWeatherTask extends AsyncTask<String, Void, WeatherData>{

        @Override
        protected WeatherData doInBackground(String... strings) {
            WeatherData wd = null;

            HttpClient httpClient = new HttpClient();
            String data;

            httpClient.initializeConnection(strings[0]);
            data = ((httpClient.getData()));

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
