package com.hsowl.seta.data;

public class NoWeatherStationException extends Exception {

    @Override
    public String getMessage(){
        return "No Weather Station found. Check your zip code.";
    }
}
