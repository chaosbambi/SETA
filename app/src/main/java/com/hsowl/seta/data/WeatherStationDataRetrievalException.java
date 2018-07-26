package com.hsowl.seta.data;

public class WeatherStationDataRetrievalException extends Exception {

    public String getMessage(){
        return "Request for weather forecast failed. API unavailable.";
    }

}
