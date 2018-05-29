package com.hsowl.seta.data;

import com.hsowl.seta.data.weatherData.WeatherData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherStation {

    private double lat;

    private double lon;

    private String weatherApiKey;

    public WeatherStation(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.weatherApiKey = SensitiveData.OPEN_WEATHER_API_KEY;
    }

    public WeatherData getWeatherData(){
        String response;
        WeatherData weatherData = null;

        String baseURL = "http://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}";
        baseURL.replace("{lat}",lat+"");
        baseURL.replace("{lon}",lon+"");
        baseURL.concat("&APPID=" + weatherApiKey);

        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(baseURL)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ( (line = br.readLine()) != null )
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            response = buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        //TODO: Fill weatherData object with Response through JSON parser

        return weatherData;
    }
}
